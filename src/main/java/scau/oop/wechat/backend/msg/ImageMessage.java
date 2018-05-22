package scau.oop.wechat.backend.msg;

import scau.oop.wechat.backend.chatroom.ChatRoom;
import scau.oop.wechat.backend.chatroom.Person;

import java.util.Date;

/**
 * 图片消息
 * @author:czfshine
 * @date:2018/3/12 14:50
 */

public class ImageMessage extends Message {

    private String filepath;

    public ImageMessage(Date time, Person talker, ChatRoom chatRoom, String filepath) {
        super(time, talker, chatRoom);
        this.filepath = filepath;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }
}
