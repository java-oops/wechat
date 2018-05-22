
package scau.oop.wechat.gui;
/**
 * @author:czfshine
 * @date:2018/4/29 0:47
 */
import com.jfoenix.controls.JFXListCell;
import com.jfoenix.controls.JFXListView;
import io.datafx.controller.ViewController;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import scau.oop.wechat.backend.chatroom.Concat;
import scau.oop.wechat.backend.chatroom.Group;
import scau.oop.wechat.backend.chatroom.Person;

import javax.annotation.PostConstruct;

@ViewController("/fxml/ChatPlane.fxml")
public class ChatPlaneController {
    @FXML
    private StackPane mainpane;
    @FXML
    private WebView webview;

    @FXML
    private JFXListView list1;

    @PostConstruct
    public void init() {
        thiss=this;
        final WebEngine webEngine = webview.getEngine();

        webEngine.load(Main.class.getResource("/chat-webrtc/index.html").toExternalForm());

        list1.addEventFilter(MouseEvent.MOUSE_CLICKED,e->{
            System.out.println(((Label)((JFXListCell)e.getTarget()).getItem()).getText());
            webEngine.executeScript("addMessage(\"\",\"11\");");
        });

    }

    private static ChatPlaneController thiss;

    public static ChatPlaneController getThis(){
        return thiss;
    }
    public void setAllConcat(Concat[] concats){
        ObservableList items = list1.getItems();
        items.remove(0,items.size());

        for(Concat concat:concats){
            Label label = new Label();
            if(concat instanceof Person){
                label.setText(((Person) concat).getNickname());
            }else if(concat instanceof Group){
                label.setText(((Group) concat).getGroupName());
            }else{
                label.setText(concat.getUUID());
            }
            label.setOnMouseClicked(e->{
                System.out.println(e.toString());
            });
            label.addEventFilter(Event.ANY,E->{
                System.out.println(E.toString());
            });
            items.add(label);
        }
        //mainpane.layout();
    }
}
