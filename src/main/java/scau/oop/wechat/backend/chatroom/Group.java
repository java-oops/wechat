package scau.oop.wechat.backend.chatroom;

/**
 * @author:czfshine
 * @date:2018/5/20 13:07
 */

public class Group extends Concat {
    private String groupName;

    public Group(String uuid) {
        super(uuid);
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
        this.setDisplayName(groupName);
    }
}
