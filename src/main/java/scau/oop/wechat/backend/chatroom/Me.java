package scau.oop.wechat.backend.chatroom;

/**
 * @author:czfshine
 * @date:2018/5/21 21:10
 */

public class Me implements User {
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getUUID() {
        return "me";
    }
}
