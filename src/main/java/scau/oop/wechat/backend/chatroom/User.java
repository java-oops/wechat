package scau.oop.wechat.backend.chatroom;

/**
 * @author:czfshine
 * @date:2018/5/8 16:58
 */

public class User {
    private String uuid;
    public User(String uuid){
        this.uuid=uuid;
    }
    public  String getUUID(){
        return uuid;
    }
     public  void setUUID(String uuid){
        this.uuid=uuid;
     }
}
