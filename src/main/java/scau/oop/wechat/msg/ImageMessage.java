package scau.oop.wechat.msg;

import scau.oop.wechat.chatroom.ChatRoom;
import scau.oop.wechat.chatroom.Person;

import java.util.Date;

/**
 * @author:czfshine
 * @date:2018/3/12 14:50
 */

public class ImageMessage extends Message {
    private String filepath;

    public ImageMessage(Date time, Person talker, ChatRoom chatRoom, String filepath) {
        super(time, talker, chatRoom);
        this.filepath = filepath;
    }
}
