package scau.oop.wechat.backend;

import com.rocketchat.common.data.model.ErrorObject;
import com.rocketchat.core.callback.LoginListener;
import com.rocketchat.core.model.TokenObject;
import com.rocketchat.livechat.callback.AuthListener;
import com.rocketchat.livechat.model.GuestObject;
import com.rocketchat.common.listener.*;
import com.rocketchat.core.*;
import scau.oop.wechat.backend.*;
import scau.oop.wechat.backend.Listener;
import scau.oop.wechat.backend.msg.Message;
import scau.oop.wechat.config.Config;

/**
 * @author:czfshine
 * @date:2018/5/12 9:40
 */

public class RocketBackend implements Backend, ConnectListener, LoginListener {

    private static Config theConfig;

    static {
        theConfig=Config.getConfig();
        theConfig.registerConfig("BackendUrl","http://123.207.235.153:3000");
    }

    RocketChatAPI client;
    private static String serverurl="http://123.207.235.153:3000";
    private static String username=System.getenv("ROCKETCHATNAME");
    private static String password=System.getenv("ROCKETCHATPWD");

    public void call(){
        System.out.println("Start Calling");
        client=new RocketChatAPI(serverurl);
        client.setReconnectionStrategy(null);
        client.connect(this);
    }


    @Override
    public void onConnect(String sessionID) {
        System.out.println("Connected to server");
        client.login(username,password,this);
    }


    @Override
    public void onDisconnect(boolean closedByServer) {
        System.out.println("Disconnected to server");
    }

    @Override
    public void onConnectError(Exception websocketException) {
        websocketException.printStackTrace();
        System.out.println("Connect Error to server");
    }
    @Override
    public void onLogin(TokenObject token, ErrorObject error) {
        if (error==null) {
            System.out.println("Logged in successfully, returned token "+ token.getAuthToken());
        }else{
            System.out.println("Got error "+error.getMessage());
        }
    }

    @Override
    public boolean login() {
        call();
        return false;
    }


    @Override
    public boolean logout() {
        return false;
    }

    @Override
    public User getUserInfo() {
        return null;
    }

    @Override
    public Concat[] getAllConcats() {
        return new Concat[0];
    }

    @Override
    public Message[] getRecentMessage() {
        return new Message[0];
    }

    @Override
    public void registerListener(Listener listener) {

    }

    @Override
    public void sendMessage(Message message, Callback callback) {

    }

    @Override
    public void sendMessage(Message message) {

    }



}
