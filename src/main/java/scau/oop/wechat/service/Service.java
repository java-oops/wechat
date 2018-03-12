package scau.oop.wechat.service;

import scau.oop.wechat.chatroom.ChatRoom;
import scau.oop.wechat.chatroom.Person;
import scau.oop.wechat.msg.Message;
import java.util.concurrent.Callable;

/**
 * @author:czfshine
 * @date:2018/3/12 14:54
 */

public class Service {
    public void Login(){

    }

    public void Logout(){

    }
    public void getUserInfo(){

    }

    public Message[] getMessages(){

        return null;
    }

    public void listen(Callable<Message> callback){

    }
    public boolean postMessage(Message msg){
        return false;
    }
    public String getImage(Message msg){
        return null;
    }
    public String getVideo(Message msg){
        return null;
    }
    public ChatRoom[] getAllChatRoom(){
        return null;
    }
    public Person[] getAllPerson(){
        return null;
    }


}
