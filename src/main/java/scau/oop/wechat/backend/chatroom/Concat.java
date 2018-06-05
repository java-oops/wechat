package scau.oop.wechat.backend.chatroom;

/**
 * @author:czfshine
 * @date:2018/5/8 16:59
 */

public abstract class Concat extends User {

    private String DisplayName;

    public Concat(String uuid) {
        super(uuid);
    }


    public String getDisplayName() {
        return DisplayName;
    }

    public void setDisplayName(String displayName) {
        DisplayName = displayName;
    }
}
