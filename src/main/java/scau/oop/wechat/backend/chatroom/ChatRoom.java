package scau.oop.wechat.backend.chatroom;
import scau.oop.wechat.backend.msg.Message;
import java.util.Date;

/**
 * 聊天室
 * @author:czfshine
 * @date:2018/3/12 14:52
 */

public class ChatRoom extends Concat {
    public Person[] getPersons() {
        return persons;
    }

    private Person[] persons; //成员列表
    private String name; 
    private String avatarPath; //头像图片路径
    private Date time; //最后聊天时间
    private Message[] msgs;
}
