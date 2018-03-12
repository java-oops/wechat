package scau.oop.wechat.client;

import scau.oop.wechat.chatroom.ChatRoom;
import scau.oop.wechat.chatroom.Person;
import scau.oop.wechat.msg.Message;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author:czfshine
 * @date:2018/3/12 15:03
 */

public class Client {

    private String datapath;

    public Client() {
        init();
    }

    private void init()  {
        try {
            MessageDataConnection = DriverManager.getConnection("jdbc:sqlite:"+datapath);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        createTable();
    }
    private void createTable(){

    }

    private Connection MessageDataConnection;

    public Message[]  getAllMessage(){
        return null;
    }

    public Message[] getAllMessage(Person person){
        return null;
    }

    public ChatRoom[] getAllChatRoom(){
        return null;
    }

    public String getAvatarFilePath(Person person){
        return null;
    }

    public void Clean(){

    }


    private void addMessage(){

    }
    private void addImage(Message msg){

    }
    private void addVideo(Message msg){

    }
}
