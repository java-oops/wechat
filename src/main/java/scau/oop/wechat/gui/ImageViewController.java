package scau.oop.wechat.gui;

import io.datafx.controller.ViewController;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.annotation.PostConstruct;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author:czfshine
 * @date:2018/5/1 16:54
 */
@ViewController(value="/fxml/ImageView.fxml")
public class ImageViewController {

    @FXML
    private ImageView imageview;

    @FXML
    private ScrollPane sp;
    @PostConstruct
    public void init() {

        Image i=new Image( "IMG_20170304_132010_HDR.jpg");

        imageview.setImage(i);

        sp.setVvalue(sp.getVmax()/2);
        sp.setHvalue(sp.getHmax()/2);
        AtomicReference<Double> sx= new AtomicReference<>(1.0);
        AtomicReference<Double> sy= new AtomicReference<>(1.0);
        imageview.setOnScroll(e->{
            if(e.getDeltaY()>0){
                sx.set(sx.get() + 0.1);
                sy.set(sy.get() + 0.1);

            }else{
                sx.set(sx.get() - 0.1);
                sy.set(sy.get() - 0.1);
            }

            imageview.setScaleX(sx.get());
            imageview.setScaleY(sy.get());
            e.consume();

        });
    }
}
