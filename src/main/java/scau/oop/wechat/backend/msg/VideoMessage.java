package scau.oop.wechat.backend.msg;

import scau.oop.wechat.backend.chatroom.ChatRoom;
import scau.oop.wechat.backend.chatroom.Person;

import java.util.Date;

/**视频消息
 * @author:czfshine
 * @date:2018/3/12 14:51
 */

public class VideoMessage extends Message {
    private String filepath;

    public VideoMessage(Date time, Person talker, ChatRoom chatRoom) {
        super(time, talker, chatRoom);
    }
}
