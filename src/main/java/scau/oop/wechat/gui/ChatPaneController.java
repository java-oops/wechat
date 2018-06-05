
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
import com.rocketchat.core.callback.FileListener;
import com.rocketchat.core.callback.HistoryListener;
import com.rocketchat.core.model.FileObject;
import com.rocketchat.core.model.RocketChatMessage;
import com.rocketchat.core.model.attachment.Attachment;
import com.rocketchat.core.model.attachment.TAttachment;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import jdk.nashorn.internal.objects.annotations.Where;
import org.controlsfx.control.Notifications;
import scau.oop.wechat.backend.BackendFactory;
import scau.oop.wechat.backend.MessageListener;
import scau.oop.wechat.backend.RocketBackend;
import scau.oop.wechat.backend.chatroom.*;
import scau.oop.wechat.backend.msg.Message;
import scau.oop.wechat.config.Config;
import sun.misc.BASE64Encoder;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

@ViewController("/fxml/ChatPane.fxml")
public class ChatPaneController {
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
    @FXML
    private FontAwesomeIcon imagesel;

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

    public static String getImageStrFromUrl(String imgURL) {
        byte[] data = null;
        try {
            // 创建URL
            URL url = new URL(imgURL);
            // 创建链接
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5 * 1000);
            InputStream inStream = conn.getInputStream();
            data = new byte[inStream.available()];
            inStream.read(data);
            inStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        // 返回Base64编码过的字节数组字符串
        return encoder.encode(data);
    }

    private void placeImage(String who, String url) {
        webEngine.executeScript("addImageMessage(\"" + who + "\",\"" + url + "\");");
    }

    private String getCon(RocketChatMessage msg) {

        if (msg.getMsgType() == RocketChatMessage.Type.TEXT) {
            return msg.getMessage();
        }
        if (msg.getMsgType() == RocketChatMessage.Type.ATTACHMENT) {
            List<TAttachment> attachments = msg.getAttachments();
            if (attachments.size() <= 0) {
                return "【消息错误】";
            }

            TAttachment tAttachment = attachments.get(0);
            if (tAttachment instanceof Attachment.ImageAttachment) {
                Attachment.ImageAttachment imageAttachment = (Attachment.ImageAttachment) tAttachment;
                String baseUrl = ((Config.StringConfigItem) Config.getConfig().getConfigItem("BackendUrl")).getValue();

                String res = "<img src=\'" + baseUrl + imageAttachment.getImage_url() + "\'>";

                return res;
            }

        }
        return "[不支持的消息类型" + msg.getMsgType().toString() + "，请给作者加鸡腿，鼓励他支持]";
    }
    private void getHistoryToChatView() {
        RocketBackend rc = (RocketBackend) BackendFactory.getBackend(Main.ROCKETID);
        RocketChatAPI.ChatRoom chatRoom = rc.getChatRommFactory().getChatRoomById(curConcat.getUUID());

        chatRoom.getChatHistory(1000, new Date(new Date().getTime()+1100), null, new HistoryListener() {



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
    private void notify(String where,String who,String contant){
        Node graphic = null;

        Notifications notificationBuilder = Notifications.create()
                .title("消息来了")
                .text(who+"给你发来了一条消息：\n"+contant)
                .graphic(graphic)
                .hideAfter(Duration.seconds(3))
                .position(Pos.BOTTOM_RIGHT)
                .onAction(e ->{
                    Stage stage = (Stage)  send.getScene().getWindow();
                    RocketBackend rc = (RocketBackend) BackendFactory.getBackend(Main.ROCKETID);
                    Concat[] allConcats = rc.getAllConcats();
                    Concat concat = ConcatUtils.FindConcatByUUID(allConcats, where);
                    curConcat=concat;
                    cleanChat();
                    getHistoryToChatView();
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                    stage.toFront();
                });




        notificationBuilder.darkStyle();


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

        imagesel.setOnMouseClicked((e) -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            File file = fileChooser.showOpenDialog(imagesel.getScene().getWindow());

            if (file != null) {
                placeMessage("me", "<img src=\'file:///" + file.getPath().replace("\\", "/") + "\'>");

                RocketBackend rc = (RocketBackend) BackendFactory.getBackend(Main.ROCKETID);
                RocketChatAPI.ChatRoom chatRoom = rc.getChatRommFactory().getChatRoomById(curConcat.getUUID());

                chatRoom.uploadFile(file, file.getName(), "", new FileListener() {
                    @Override
                    public void onUploadStarted(String roomId, String fileName, String description) {

                    }

                    @Override
                    public void onUploadProgress(int progress, String roomId, String fileName, String description) {

                    }

                    @Override
                    public void onUploadComplete(int statusCode, FileObject file, String roomId, String fileName, String description) {

                    }

                    @Override
                    public void onUploadError(ErrorObject error, IOException e) {

                    }

                    @Override
                    public void onSendFile(RocketChatMessage message, ErrorObject error) {

                    }
                });
                System.out.println("sel" + file.getPath());
            }
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
            msg = msg.replace("\\", "\\\\");
            msg = msg.replace("\n", "<br/>");
            System.out.println("addMessage(\"" + who + "\",\"" + msg + "\");");
            webEngine.executeScript("addMessage(\"" + who + "\",\"" + msg + "\");");
        } catch (Exception e) {
            e.printStackTrace();
        }
        //placeImage(who,"http://123.207.235.153:3000/file-upload/4gEQrzFBAodNes5aJ/%E6%9C%AA%E6%A0%87%E9%A2%98-1.png");
        //webEngine.executeScript("addMessage(\"me\",\"11\");");
    }

    private void placeMessage(String who, RocketChatMessage msg) {

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
                                try {
                                    RocketChatMessage rmsg = (RocketChatMessage) msg.getSourObj();

                                    placeMessage("me", getCon(rmsg));
                                } catch (Exception e) {
                                }


                            }
                        } else {
                            if(send.getScene().getWindow().focusedProperty().getValue()==false){
                                RocketChatMessage rmsg =(RocketChatMessage) msg.getSourObj();
                                ChatPaneController.this.notify(rmsg.getRoomId(), msg.getTalker().getUUID(),msg.getContant());
                            }

                            try {
                                RocketChatMessage rmsg = (RocketChatMessage) msg.getSourObj();
                                placeMessage(msg.getTalker().getUUID(), getCon(rmsg));

                            } catch (Exception e) {
                            }

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

    private static ChatPaneController thiss;

    public static ChatPaneController getThis(){
        return thiss;
    }

    class JSCall {
        public void init() {
            System.out.println("js init");
        }
    }
}
