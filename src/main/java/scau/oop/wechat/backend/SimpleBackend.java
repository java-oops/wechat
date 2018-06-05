package scau.oop.wechat.backend;

import scau.oop.wechat.backend.chatroom.ChatRoom;
import scau.oop.wechat.backend.chatroom.Concat;
import scau.oop.wechat.backend.chatroom.User;
import scau.oop.wechat.backend.msg.Message;

import java.util.Date;

/**
 * @author:czfshine
 * @date:2018/5/9 14:24
 */

public class SimpleBackend implements Backend {

    /*没有具体逻辑，都成功*/

    public boolean login() {
        //todo
        return true;
    }

    @Override
    public boolean login(Runnable callback) {
        return false;
    }

    public boolean logout() {
        //todo
        return true;
    }

    /**
     * 虚拟用户
     */
    public class SimpleUser extends User {

        public SimpleUser(String uuid) {
            super(uuid);
        }


    }

    public User getUserInfo() {
        return new SimpleUser("");
    }

    /**
     * 虚拟聊天室
     */
    public class SimpleChatRoom extends ChatRoom{

        public SimpleChatRoom(String uuid) {
            super(uuid);
        }
    }
    private ChatRoom[] allconcat;
    public Concat[] getAllConcats() {

        /*保留给其他方法调用*/
        ChatRoom[] res=new ChatRoom[5];
        res[0]=new SimpleChatRoom("");
        res[1]=new SimpleChatRoom("");
        res[2]=new SimpleChatRoom("");
        res[3]=new SimpleChatRoom("");
        res[4]=new SimpleChatRoom("");
        allconcat=res;
        return res;
    }

    /**
     * 虚拟消息类型
     */
    public class SimpleMessage extends Message{
        public SimpleMessage(Date time, User talker, Concat chatRoom) {
            super(time, talker, chatRoom);
        }
    }
    public Message[] getRecentMessage() {
        return new Message[]{new SimpleMessage(new Date(),new SimpleUser(""),new SimpleChatRoom(""))};
    }

    private MessageListener messageListener;

    /**
     * 模拟收到消息，定时收到
     * @param messageListener 待注册的监听器
     */
    public void registerListener(MessageListener messageListener) {
        this.messageListener = messageListener;
        startSendMessageBySelf();
    }

    /** 创建随机消息
     * @return 随机消息
     */
    private Message createRandomMessage(){
        //todo
        int r=0;
        Message msg=new Message(new Date(),allconcat[r].getPersons()[0],allconcat[r]);
        return msg;
    }
    private Thread thread;

    /**
     * 定时器，定时发送消息给本身
     */
    private class SendToSelfByTimer extends Thread{

        private final int TIME =3000;
        @Override
        public void run() {
            while(true){
                messageListener.run(createRandomMessage());
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

    /**
     * 后台发送消息进程
     */
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
