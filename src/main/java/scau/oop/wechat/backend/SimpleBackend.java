package scau.oop.wechat.backend;

import scau.oop.wechat.backend.msg.Message;

/**
 * @author:czfshine
 * @date:2018/5/9 14:24
 */

public class SimpleBackend implements Backend {
    public boolean login() {
        return false;
    }

    public boolean logout() {
        return false;
    }

    public User getUserInfo() {
        return null;
    }

    public Concat[] getAllConcats() {
        return new Concat[0];
    }

    public Message[] getRecentMessage() {
        return new Message[0];
    }

    public void registerListener(Listener listener) {

    }

    public void sendMessage(Message message, Callback callback) {

    }
}
