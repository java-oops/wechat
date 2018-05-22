package scau.oop.wechat.backend;

import java.util.HashMap;
import java.util.Map;

/**
 * 获取与创建对应id 的唯一工厂
 * @author:czfshine
 * @date:2018/5/21 21:30
 */

public class BackendFactory {

    private static Map<String,Backend> allBackend=new HashMap<>();

    /**新建后端
     * @param id 唯一标识符
     * @param clazz 具体的后端类，要实现Backend接口的
     */
    public static void createBackend(String id,Class clazz){
        //todo
        try {
            allBackend.put(id,(Backend)clazz.newInstance());
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**通过id获取唯一的后端
     * @param id 唯一id
     * @return 获取的后端
     */
    public static Backend getBackend(String id){
        return allBackend.getOrDefault(id,null);
    }
}
