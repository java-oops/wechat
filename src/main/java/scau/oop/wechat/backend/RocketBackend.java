package scau.oop.wechat.backend;

import com.rocketchat.common.data.model.ErrorObject;
import com.rocketchat.core.callback.LoginListener;
import com.rocketchat.core.callback.RoomListener;
import com.rocketchat.core.model.RoomObject;
import com.rocketchat.core.model.TokenObject;
import com.rocketchat.livechat.callback.AuthListener;
import com.rocketchat.livechat.model.GuestObject;
import com.rocketchat.common.listener.*;
import com.rocketchat.core.*;
import com.sun.istack.internal.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scau.oop.wechat.backend.*;
import scau.oop.wechat.backend.Listener;
import scau.oop.wechat.backend.msg.Message;
import scau.oop.wechat.config.Config;

import java.util.List;

import static com.sun.org.apache.xalan.internal.xsltc.compiler.sym.error;

/**
 * @author:czfshine
 * @date:2018/5/12 9:40
 */

public class RocketBackend implements Backend {


    private static Config theConfig;
    Logger logger = LoggerFactory.getLogger("RocketBackend");
    static {
        theConfig=Config.getConfig();
        theConfig.registerConfig("BackendUrl","http://123.207.235.153:3000");
    }

    RocketChatAPI client;
    private static String serverurl="http://123.207.235.153:3000";
    private static String username=System.getenv("ROCKETCHATNAME");
    private static String password=System.getenv("ROCKETCHATPWD");

    private boolean logged=false;

    private class LoginListener implements com.rocketchat.core.callback.LoginListener{
        @Override
        public void onLogin(TokenObject token, ErrorObject error) {
            if (error==null) {
                logged=true;
                logger.info("Logged in successfully, returned token "+ token.getAuthToken());
            }else{
                logged=false;
                logger.warn("Got error "+error.getMessage());
            }
        }
    }

    private  class ConnectListenerImpl implements ConnectListener{
        @Override
        public void onConnect(String sessionID) {
            logger.info("Connected to server");
            //todo
            System.out.println("username size:{} pwd size{}"+username.length()+password.length());
            client.login(username,password,new RocketBackend.LoginListener());
        }


        @Override
        public void onDisconnect(boolean closedByServer) {
            logged=false;
            logger.warn("Disconnected to server");
        }

        @Override
        public void onConnectError(Exception websocketException) {
            websocketException.printStackTrace();
            logger.error("Connect Error to server");
        }
    }

    @Override
    public boolean login() {
        logger.info("Start logging......");

        client=new RocketChatAPI(serverurl);
        client.setReconnectionStrategy(null);
        client.connect(new ConnectListenerImpl());
        return logged;
    }


    @Override
    public boolean logout() {
        return false;
    }

    @Override
    public User getUserInfo() {
        return null;
    }


    private class GetRoomListenerImpl implements RoomListener.GetRoomListener {

        @Override
        public void onGetRooms(List<RoomObject> rooms, ErrorObject errorObject) {
            if (errorObject==null){
                for (RoomObject room : rooms){
                    System.out.println("Room name is "+room.getRoomName());
                    System.out.println("Room id is "+room.getRoomId());
                    System.out.println("Room topic is "+room.getTopic());
                    System.out.println("Room type is "+ room.getRoomType());

                }
            }else{
                System.out.println("Got error "+errorObject.getMessage());
            }
        }
    }

    @Override
    @NotNull
    public Concat[] getAllConcats() {
        if(!logged){
            logger.error("Want get all concats,but it not logged.");
            //todo
            return new Concat[0];//don't null
        }
        client.getRooms(new GetRoomListenerImpl());
        return new Concat[0];//don't null
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
