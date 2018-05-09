package scau.oop.wechat.backend;

import scau.oop.wechat.backend.msg.Message;

/**
 * 后端接口，所有方法均为非阻塞的
 * @author:czfshine
 * @date:2018/5/8 16:54
 */
public interface Backend {


    /**
     * 尝试登录
     *
     * 看具体实现，可能要对线程池进行初始化，
     * 读取以前登录信息，保存这次登录的信息，
     * 或者保证单例化。
     *
     * @return 是否登录成功，其他错误信息根据实际需要自行设计
     */
    boolean login();

    /**
     * 尝试登出
     *
     * 要对本次会话所申请的资源进行释放，
     * 比如注销所有监听线程，清空和保存消息队列，
     * 保存会话信息以便下次登录等
     *
     * @see Backend#login
     *
     * @return 是否成功
     */
    boolean logout();


    /**
     * 获取基本的用户信息
     * @return 用户对象
     */
    User getUserInfo();

    /**
     * 获取联系人列表
     * @return 联系人数组
     */
    Concat[] getAllConcats();

    /**
     * 获取登陆前的未读消息
     * @return 消息列表
     */
    Message[] getRecentMessage();

    /*基础功能*/

    /**
     * 增加消息监听器
     * 有新消息到来会开个新线程调用该监听器进行处理，注意处理并发情况。
     * @param listener 待注册的监听器
     */
    void registerListener(Listener listener);


    /**
     * 发送消息，发送完会调用callback函数
     * 要发送的对象，内容都封装在Message对象里面，注意是多线程的
     * @param message 待发送的信息对象
     * @param callback 回调函数
     */
    void sendMessage(Message message,Callback callback);

    /**
     * 发送消息，注意是多线程的
     * @param message 待发送的信息对象
     */
    void sendMessage(Message message);

}
