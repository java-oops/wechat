package scau.oop.wechat.backend.msg;

import scau.oop.wechat.backend.Concat;
import scau.oop.wechat.backend.User;
import scau.oop.wechat.backend.chatroom.ChatRoom;
import scau.oop.wechat.backend.chatroom.Person;

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

}
