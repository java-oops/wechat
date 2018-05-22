package scau.oop.wechat.backend.msg;

import scau.oop.wechat.backend.chatroom.ChatRoom;
import scau.oop.wechat.backend.chatroom.Person;

import java.util.Date;

/**
 * @author:czfshine
 * @date:2018/3/12 14:50
 */

public class TextMessage extends Message {
    private String content;

    public TextMessage(Date time, Person talker, ChatRoom chatRoom) {
        super(time, talker, chatRoom);
    }
}
