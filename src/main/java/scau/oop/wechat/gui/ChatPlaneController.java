
package scau.oop.wechat.gui;
/**
 * @author:czfshine
 * @date:2018/4/29 0:47
 */

import com.jfoenix.controls.JFXListCell;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
import com.rocketchat.common.data.model.ErrorObject;
import com.rocketchat.core.RocketChatAPI;
import com.rocketchat.core.callback.HistoryListener;
import com.rocketchat.core.model.RocketChatMessage;
import io.datafx.controller.ViewController;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import scau.oop.wechat.backend.BackendFactory;
import scau.oop.wechat.backend.MessageListener;
import scau.oop.wechat.backend.RocketBackend;
import scau.oop.wechat.backend.chatroom.*;
import scau.oop.wechat.backend.msg.Message;
import scau.oop.wechat.config.Config;

import javax.annotation.PostConstruct;
import java.util.*;

@ViewController("/fxml/ChatPlane.fxml")
public class ChatPlaneController {
    @FXML
    private StackPane mainpane;
    @FXML
    private WebView webview;

    @FXML
    private JFXListView list1;

    @FXML
    private Button send;

    @FXML
    private JFXTextArea jfxTextArea;

    private Concat curConcat;

    private WebEngine webEngine ;


    private Map<Concat, List<Message>> msglist = new HashMap<>();
    private String newMsg = "";

    private void cleanChat() {
        webEngine.executeScript("cleanMessage();");

    }

    private void initCurConcat() {
        cleanChat();
        RocketBackend rc = (RocketBackend) BackendFactory.getBackend(Main.ROCKETID);
        Concat[] allConcats = rc.getAllConcats();
        curConcat = allConcats[0];
        getHistoryToChatView();

    }

    private void getHistoryToChatView() {
        RocketBackend rc = (RocketBackend) BackendFactory.getBackend(Main.ROCKETID);
        RocketChatAPI.ChatRoom chatRoom = rc.getChatRommFactory().getChatRoomById(curConcat.getUUID());

        chatRoom.getChatHistory(1000, new Date(new Date().getTime()), null, new HistoryListener() {

            private String getCon(RocketChatMessage msg) {

                if (msg.getMsgType() == RocketChatMessage.Type.TEXT) {
                    return msg.getMessage();
                }
                return "[不支持的消息类型" + msg.getMsgType().toString() + "，请给作者加鸡腿，鼓励他支持]";
            }

            @Override
            public void onLoadHistory(List<RocketChatMessage> list, int unreadNotLoaded, ErrorObject error) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("get history");
                        Config.StringConfigItem backendUername = (Config.StringConfigItem) Config.getConfig().getConfigItem("BackendUername");
                        ArrayList<Message> msgl = new ArrayList<>();
                        try {
                            for (int i = list.size() - 1; i >= 0; i--) {
                                RocketChatMessage msg = list.get(i);
                                if (msg.getSender().getUserName().equals(backendUername.getValue())) {
                                    placeMessage("me", getCon(msg));
                                } else {
                                    placeMessage(msg.getSender().getUserName(), getCon(msg));
                                }

                                msgl.add(new Message(msg.getMsgTimestamp(), new User(msg.getSender().getUserName()), null));
                                System.out.println(msg.getMessage());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        msglist.put(curConcat, msgl);
                    }
                });

            }
        });
    }

    private void setMsgFromList() {
        List<Message> messages = msglist.get(curConcat);
        for (int i = messages.size() - 1; i >= 0; i--) {

        }
    }
    private void notify(String who,String contant){
        Node graphic = null;

        Notifications notificationBuilder = Notifications.create()
                .title("消息来了")
                .text(who+"给你发来了一条消息：\n"+contant)
                .graphic(graphic)
                .hideAfter(Duration.seconds(3))
                .position(Pos.BOTTOM_RIGHT)
                .onAction(e ->{
                    Stage stage = (Stage)  send.getScene().getWindow();
                    stage.toFront();
                });




        //notificationBuilder.darkStyle();


        notificationBuilder.showInformation();
    }
    private void sendMsg() {
        RocketBackend rc = (RocketBackend) BackendFactory.getBackend(Main.ROCKETID);
        RocketChatAPI.ChatRoom chatRoom = rc.getChatRommFactory().getChatRoomById(curConcat.getUUID());
        String text = jfxTextArea.getText();
        newMsg = text;
        chatRoom.sendMessage(text);
        placeMessage("me", text);
        jfxTextArea.setText("");
    }

    @PostConstruct
    public void init() {
        thiss=this;
        webEngine = webview.getEngine();
        webEngine.load(Main.class.getResource("/chat-webrtc/index.html").toExternalForm());

        list1.addEventFilter(MouseEvent.MOUSE_CLICKED,e->{
            String name = ((Label) ((JFXListCell) e.getTarget()).getItem()).getText();
            RocketBackend rc=(RocketBackend) BackendFactory.getBackend(Main.ROCKETID);
            Concat[] allConcats = rc.getAllConcats();
            Concat concat = ConcatUtils.FindConcatByDisplayName(allConcats, name);
            curConcat=concat;
            cleanChat();
            getHistoryToChatView();
            /*
            if(msglist.containsKey(concat)){
                setMsgFromList();
            }
            else{
                getHistoryToChatView();
            }
        */
        });

        send.setOnAction(e->{
            sendMsg();
        });
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        initCurConcat();
                        timer.cancel();
                        webEngine.executeScript("toBottom();");
                    }

                });
            }
        }, 300, 5000);


    }

    @FXML
    public void onKey(KeyEvent ke) {
        if (ke.getCode() == KeyCode.ENTER) {
            sendMsg();
            ke.consume();
        }
    }

    private void placeMessage(String who, String msg) {
        try {
            webEngine.executeScript("addMessage(\"" + who + "\",\"" + msg + "\");");
        } catch (Exception e) {
            e.printStackTrace();
        }
        //webEngine.executeScript("addMessage(\"me\",\"11\");");
    }

    private void placeMessage(RocketChatMessage msg) {

    }

    public void setAllConcat(Concat[] concats){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                ObservableList items = list1.getItems();
                items.remove(0, items.size());

                for (Concat concat : concats) {
                    Label label = new Label();
                    if (concat instanceof Person) {
                        label.setText(((Person) concat).getNickname());
                    } else if (concat instanceof Group) {
                        label.setText(((Group) concat).getGroupName());
                    } else {
                        label.setText(concat.getUUID());
                    }
                    label.setOnMouseClicked(e -> {
                        System.out.println(e.toString());
                    });
                    label.addEventFilter(Event.ANY, E -> {
                        System.out.println(E.toString());
                    });
                    items.add(label);
                }
                RocketBackend rc = (RocketBackend) BackendFactory.getBackend(Main.ROCKETID);
                rc.registerListener(new MessageListener() {
                    @Override
                    public void run(Message msg) {

                        Config.StringConfigItem backendUername = (Config.StringConfigItem) Config.getConfig().getConfigItem("BackendUername");
                        if (msg.getTalker().getUUID().equals(backendUername.getValue())) {
                            if (!newMsg.equals(msg.getContant())) {
                                placeMessage("me", msg.getContant());
                            }
                        } else {
                            if(send.getScene().getWindow().focusedProperty().getValue()==false){
                                ChatPlaneController.this.notify(msg.getTalker().getUUID(),msg.getContant());
                            }

                            placeMessage(msg.getTalker().getUUID(), msg.getContant());
                        }
                        System.out.println("getmsg" + msg.getTalker().getUUID() + curConcat.getDisplayName());
                    }
                });
            }
        });

        //mainpane.layout();
    }

    private void placeMessage(Message message){
        //todo
        message.getTalker();
        webEngine.executeScript("addMessage(\"me\",\"11\");");
    }

    private static ChatPlaneController thiss;

    public static ChatPlaneController getThis(){
        return thiss;
    }

    class JSCall {
        public void init() {
            System.out.println("js init");
        }
    }
}
