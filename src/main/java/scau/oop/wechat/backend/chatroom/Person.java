package scau.oop.wechat.backend.chatroom;

import java.util.Date;

/**
 * @author:czfshine
 * @date:2018/3/12 14:53
 */

public class Person extends Concat  implements User {
    private String avatarPath;
    private String nickname;
    private Date time;

    public Person getPerson(String UUID){
        return null;//todo
    }



    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
