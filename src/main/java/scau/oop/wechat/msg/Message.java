package scau.oop.wechat.msg;

import scau.oop.wechat.chatroom.ChatRoom;
import scau.oop.wechat.chatroom.Person;

import java.util.Date;

/**
 * @author:czfshine
 * @date:2018/3/12 14:47
 */

public class Message {
    private Date time;
    private Person talker;

    public Message(Date time, Person talker, ChatRoom chatRoom) {
        this.time = time;
        this.talker = talker;
        this.chatRoom = chatRoom;
    }

    private ChatRoom chatRoom;

}
