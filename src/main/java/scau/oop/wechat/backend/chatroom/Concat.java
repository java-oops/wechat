package scau.oop.wechat.backend.chatroom;

/**
 * @author:czfshine
 * @date:2018/5/8 16:59
 */

public abstract class Concat {
    protected String UUID;
    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }
    private String DisplayName;

    public String getDisplayName() {
        return DisplayName;
    }

    public void setDisplayName(String displayName) {
        DisplayName = displayName;
    }
}
