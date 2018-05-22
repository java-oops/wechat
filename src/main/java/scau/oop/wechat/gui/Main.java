package scau.oop.wechat.gui;

import com.jfoenix.controls.JFXDecorator;
import com.jfoenix.svg.SVGGlyph;
import io.datafx.controller.flow.Flow;
import io.datafx.controller.flow.FlowException;
import io.datafx.controller.flow.container.DefaultFlowContainer;
import io.datafx.controller.flow.context.FXMLViewFlowContext;
import io.datafx.controller.flow.context.ViewFlowContext;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import scau.oop.wechat.backend.Backend;
import scau.oop.wechat.backend.BackendFactory;
import scau.oop.wechat.backend.RocketBackend;
import scau.oop.wechat.backend.chatroom.Concat;

/**
 * @author:czfshine
 * @date:2018/3/12 15:16
 */

public class Main extends Application {

    @FXMLViewFlowContext
    private ViewFlowContext flowContext;

    @Override
    public void start(Stage stage) throws Exception {
        initStyle(stage);
        initBackend();
        RocketBackend rc=(RocketBackend) BackendFactory.getBackend(ROCKETID);
        
        stage.show();
    }

    private void setAllInfo(){
        System.out.println("start set info");
        try{
            RocketBackend rc=(RocketBackend) BackendFactory.getBackend(ROCKETID);
            Concat[] allConcats = rc.getAllConcats();
            ChatPlaneController.getThis().setAllConcat(allConcats);
        }catch (Exception e){
            e.printStackTrace();
        }


    }
    private final String ROCKETID="rockedid1";
    private void initBackend(){
        BackendFactory.createBackend(ROCKETID,RocketBackend.class);
        RocketBackend rc=(RocketBackend) BackendFactory.getBackend(ROCKETID);
        rc.login(()->{
            rc.Refresh(()->{
                setAllInfo();
            });
        });

    }

    /**
     * 初始化界面样式
     * @param stage
     * @throws FlowException
     */
    private void initStyle(Stage stage) throws FlowException {
        //Flow flow = new Flow(MainWindowController.class);
        Flow flow =new Flow(ChatPlaneController.class);
        DefaultFlowContainer container = new DefaultFlowContainer();
        flowContext = new ViewFlowContext();
        flowContext.register("Stage", stage);
        flow.createHandler(flowContext).start(container);

        JFXDecorator decorator = new JFXDecorator(stage, container.getView());
        decorator.setCustomMaximize(true);
        decorator.setGraphic(new SVGGlyph(""));

        decorator.setAlignment(Pos.TOP_LEFT);
        stage.setTitle("走心聊天");

        double width = 800;
        double height = 600;
        try {
            Rectangle2D bounds = Screen.getScreens().get(0).getBounds();
            width = bounds.getWidth() / 2.5;
            height = bounds.getHeight() / 1.35;
        }catch (Exception e){ }


        Scene scene = new Scene(decorator, width, height);
        final ObservableList<String> stylesheets = scene.getStylesheets();

        stylesheets.addAll(Main.class.getResource("/css/jfoenix-fonts.css").toExternalForm(),
                Main.class.getResource("/css/jfoenix-design.css").toExternalForm(),
                Main.class.getResource("/css/jfoenix-main-demo.css").toExternalForm(),
                Main.class.getResource("/css/chat.css").toExternalForm());


        stage.setScene(scene);
    }
}
