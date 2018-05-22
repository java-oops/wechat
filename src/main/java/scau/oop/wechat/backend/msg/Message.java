package scau.oop.wechat.backend.msg;

import scau.oop.wechat.backend.chatroom.Concat;
import scau.oop.wechat.backend.chatroom.User;

import java.util.Date;

/**
 * @author:czfshine
 * @date:2018/3/12 14:47
 */

public class Message {
    private Date time;
    private User talker;

    public Message(Date time, User talker, Concat chatRoom) {
        this.time = time;
        this.talker = talker;
        this.concat = chatRoom;
    }

    private Concat concat;

    private String contant;

    public Concat getConcat() {
        return concat;
    }

    public void setConcat(Concat concat) {
        this.concat = concat;
    }

    public String getContant() {
        return contant;
    }

    public void setContant(String contant) {
        this.contant = contant;
    }

    public User getTalker() {
        return talker;
    }

    public void setTalker(User talker) {
        this.talker = talker;
    }
}
