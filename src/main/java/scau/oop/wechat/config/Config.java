package scau.oop.wechat.config;

import javafx.scene.input.KeyEvent;

import java.util.HashMap;
import java.util.Map;

/**
 * @author:czfshine
 * @date:2018/3/12 15:10
 */

public class Config {

    private String dataPath;
    private String imageDir;
    private String videoDir;

    private static  abstract class ConfigItem<E>{
        public abstract E getValue();
        public abstract void SetValue(E value);
    }

    public static class StringConfigItem extends ConfigItem<String>{

        private String value;


        public StringConfigItem(String v){
            SetValue(v);
        }
        @Override
        public String getValue() {
            return value;
        }

        @Override
        public void SetValue(String value) {
            this.value=value;
        }
    }

    private static class ConfigItemFactory{
        public static ConfigItem makeItem(Object value){
            if(value instanceof String){
                StringConfigItem stringConfigItem=new StringConfigItem((String) value);
                return stringConfigItem;
            }
            //todo
            return null;
        }
    }

    private Map<String,ConfigItem> allConfig=new HashMap<>();

    private static Config config;
    static {
        config=new Config();
    }

    public static Config getConfig(){
        return config;
    }


    private Config(){

    }

    public void registerConfig(String name,Object defaultValue ){
        allConfig.put(name,ConfigItemFactory.makeItem(defaultValue));
    }
    public Object getConfigItem(String name){
        return allConfig.getOrDefault(name,null);
    }

}
