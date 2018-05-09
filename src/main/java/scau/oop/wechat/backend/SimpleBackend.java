package scau.oop.wechat.backend;

import scau.oop.wechat.backend.chatroom.ChatRoom;
import scau.oop.wechat.backend.msg.Message;

import java.util.Date;

/**
 * @author:czfshine
 * @date:2018/5/9 14:24
 */

public class SimpleBackend implements Backend {
    public boolean login() {
        return true;
    }

    public boolean logout() {
        return true;
    }

    public class SimpleUser extends User{

    }
    public User getUserInfo() {
        return new SimpleUser();
    }


    public class SimpleChatRoom extends ChatRoom{

    }
    private ChatRoom[] allconcat;
    public Concat[] getAllConcats() {
        ChatRoom[] res=new ChatRoom[5];
        res[0]=new SimpleChatRoom();
        res[1]=new SimpleChatRoom();
        res[2]=new SimpleChatRoom();
        res[3]=new SimpleChatRoom();
        res[4]=new SimpleChatRoom();
        allconcat=res;
        return res;
    }

    public class SimpleMessage extends Message{
        public SimpleMessage(Date time, User talker, Concat chatRoom) {
            super(time, talker, chatRoom);
        }
    }
    public Message[] getRecentMessage() {
        return new Message[]{new SimpleMessage(new Date(),new SimpleUser(),new SimpleChatRoom())};
    }

    private Listener listener;

    public void registerListener(Listener listener) {
        this.listener=listener;
        startSendMessageBySelf();
    }

    private Message createRandomMessage(){
        //todo
        int r=0;
        Message msg=new Message(new Date(),allconcat[r].getPersons()[0],allconcat[r]);
        return msg;
    }
    private Thread thread;
    private class SendToSelfByTimer extends Thread{

        private final int TIME =3000;
        @Override
        public void run() {
            while(true){
                listener.run(createRandomMessage());
                try {
                    sleep(TIME);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private void startSendMessageBySelf(){
        if(this.thread==null){
            thread=new SendToSelfByTimer();
            thread.start();
        }
    }

    public void sendMessage(Message message, Callback callback) {
        sendmeg(message,callback);
    }

    public void sendMessage(Message message) {
        sendmeg(message,null);
    }

    private class SendThread extends Thread{
        private Callback callback;
        private Message message;
        public SendThread(Callback callback, Message message) {
            this.callback = callback;
            this.message = message;
        }

        public SendThread(Message message){
            this.message=message;
        }

        @Override
        public void run() {
            try {
                sleep(500);
                if(callback!=null){
                    callback.run();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    private void sendmeg(Message message,Callback callback){
        new SendThread(callback, message);
    }
}
