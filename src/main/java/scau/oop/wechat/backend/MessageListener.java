package scau.oop.wechat.backend;

import scau.oop.wechat.backend.msg.Message;

/**通用消息监听器
 * @author:czfshine
 * @date:2018/5/9 12:49
 */

public abstract class MessageListener implements Runnable{
    public abstract void run(Message msg);
}
