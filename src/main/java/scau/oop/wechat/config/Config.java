package scau.oop.wechat.config;

import javafx.scene.input.KeyEvent;

import java.util.Map;

/**
 * @author:czfshine
 * @date:2018/3/12 15:10
 */

public class Config {

    private String dataPath;
    private String imageDir;
    private String videoDir;

    private Map<KeyEvent,Runnable> keyEventRunnableMap;

    private static Config config;
    static {
        config=new Config();
    }

    public Config getConfig(){
        return config;
    }


    private Config(){

    }

}
