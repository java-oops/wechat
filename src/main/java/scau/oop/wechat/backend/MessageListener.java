package scau.oop.wechat.backend;

import scau.oop.wechat.backend.msg.Message;

/**
 * @author:czfshine
 * @date:2018/5/9 12:49
 */

public abstract class MessageListener {
    public abstract void run(Message msg);
}
