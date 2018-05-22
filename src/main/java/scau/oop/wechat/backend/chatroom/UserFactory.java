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
 * @author:czfshine
 * @date:2018/5/9 16:14
 */

public class UserFactory {
    private static Map<Backend,Map<String,User>> alluser= new HashMap<>();

    private static User createNewUser(){
        return null;
    }
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

    private static DefaultBackend defaultBackend=new DefaultBackend();
    public static User getUser(String uuid){
        return getUser(defaultBackend,uuid);
    }

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
