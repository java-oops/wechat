package scau.oop.wechat.backend.chatroom;

import scau.oop.wechat.backend.Backend;
import scau.oop.wechat.backend.Callback;
import scau.oop.wechat.backend.MessageListener;
import scau.oop.wechat.backend.chatroom.Concat;
import scau.oop.wechat.backend.chatroom.User;
import scau.oop.wechat.backend.msg.Message;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户工厂
 * @author:czfshine
 * @date:2018/5/9 16:14
 */

public class UserFactory {
    //保存不同后端的用户
    private static Map<Backend,Map<String,User>> alluser= new HashMap<>();

    /**
     * 创建新用户
     * @return
     */
    private static User createNewUser(){
        //todo
        return null;
    }

    /**
     * 得到中对应后端的用户对象
     * @param backend 要获取的后端
     * @param uuid 用户唯一标识符
     * @return 获取到的对象
     */
    public static User getUser(Backend backend,String uuid){
        if(alluser.getOrDefault(backend,null)!=null){
            if(alluser.get(backend).getOrDefault(uuid,null)!=null){
                return alluser.get(backend).get(uuid);
            }else{
                User user =createNewUser();
                alluser.get(backend).put(uuid,user);
                return user;
            }
        }else{
            alluser.put(backend,new HashMap<>());
            return getUser(backend,uuid);
        }
    }

    /**
     * 增加对应后端的用户对象
     * @param backend 要获取的后端
     * @param uuid 用户唯一标识符
     * @return 获取到的对象
     */
    public static void addUser(Backend backend,String uuid,User user){
        if(alluser.getOrDefault(backend,null)!=null){
            if(alluser.get(backend).getOrDefault(uuid,null)!=null){
                alluser.get(backend).put(uuid,user);
                return ;
            }
        }else{
            alluser.put(backend,new HashMap<>());
            getUser(backend,uuid);
        }
    }


    private static DefaultBackend defaultBackend=new DefaultBackend();

    /**
     * 从默认后端获取对象
     * @param uuid 唯一标识符
     * @return 对象
     */
    public static User getUser(String uuid){
        return getUser(defaultBackend,uuid);
    }


    /**
     * 默认后端，没有指定后端的使用这个
     */
    private static class DefaultBackend implements Backend {

        @Override
        public boolean login() {
            return false;
        }

        @Override
        public boolean login(Runnable callback) {
            return false;
        }

        @Override
        public boolean logout() {
            return false;
        }

        @Override
        public User getUserInfo() {
            return null;
        }

        @Override
        public Concat[] getAllConcats() {
            return new Concat[0];
        }

        @Override
        public Message[] getRecentMessage() {
            return new Message[0];
        }

        @Override
        public void registerListener(MessageListener messageListener) {

        }

        @Override
        public void sendMessage(Message message, Callback callback) {

        }

        @Override
        public void sendMessage(Message message) {

        }
    }

}
