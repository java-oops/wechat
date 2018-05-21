/**
 * @author:czfshine
 * @date:2018/4/29 0:47
 */
package scau.oop.wechat.gui;

import com.jfoenix.controls.JFXListView;
import io.datafx.controller.ViewController;
import javafx.fxml.FXML;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import javax.annotation.PostConstruct;

@ViewController("/fxml/ChatPlane.fxml")
public class ChatPlaneController {
    @FXML
    private WebView webview;

    @FXML
    private JFXListView list2;

    @PostConstruct
    public void init() {
        final WebEngine webEngine = webview.getEngine();
        webEngine.load("file:///C:/Users/czfshine/Downloads/chat-webrtc/index.html");
    }
}
