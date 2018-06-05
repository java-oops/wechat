package scau.oop.wechat.backend.chatroom;

/**
 * @author:czfshine
 * @date:2018/5/21 21:10
 */

public class Me extends User {
    private String username;

    public Me(String uuid) {
        super(uuid);
    }

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
