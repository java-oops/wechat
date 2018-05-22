package scau.oop.wechat.backend;

import java.util.HashMap;
import java.util.Map;

/**
 * @author:czfshine
 * @date:2018/5/21 21:30
 */

public class BackendFactory {
    private static Map<String,Backend> allBackend=new HashMap<>();

    public static void createBackend(String id,Class clazz){

        try {
            allBackend.put(id,(Backend)clazz.newInstance());
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
    public static Backend getBackend(String id){
        return allBackend.getOrDefault(id,null);
    }
}
