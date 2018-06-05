package scau.oop.wechat.backend.chatroom;
import scau.oop.wechat.backend.msg.Message;
import java.util.Date;

/**
 * 聊天室
 * @author:czfshine
 * @date:2018/3/12 14:52
 */

public class ChatRoom extends Concat {
    public ChatRoom(String uuid) {
        super(uuid);
    }

    public Person[] getPersons() {
        return persons;
    }

    public void setPersons(Person[] persons) {
        this.persons = persons;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Message[] getMsgs() {
        return msgs;
    }

    public void setMsgs(Message[] msgs) {
        this.msgs = msgs;
    }

    private Person[] persons; //成员列表
    private String name; 
    private String avatarPath; //头像图片路径
    private Date time; //最后聊天时间
    private Message[] msgs;
}
