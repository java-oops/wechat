package scau.oop.wechat.backend;

import com.rocketchat.common.data.model.ErrorObject;
import com.rocketchat.common.data.model.Room;
import com.rocketchat.common.listener.ConnectListener;
import com.rocketchat.common.listener.SubscribeListener;
import com.rocketchat.core.RocketChatAPI;
import com.rocketchat.core.callback.GetSubscriptionListener;
import com.rocketchat.core.factory.ChatRoomFactory;
import com.rocketchat.core.model.RocketChatMessage;
import com.rocketchat.core.model.SubscriptionObject;
import com.rocketchat.core.model.TokenObject;
import com.sun.istack.internal.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scau.oop.wechat.backend.chatroom.*;
import scau.oop.wechat.backend.msg.Message;
import scau.oop.wechat.config.Config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
    private Me me;
    private Runnable loggincallback;
    private class LoginListenerImpl implements com.rocketchat.core.callback.LoginListener{
        @Override
        public void onLogin(TokenObject token, ErrorObject error) {
            if (error==null) {
                logged=true;
                logger.info("Logged in successfully, returned token "+ token.getAuthToken());
                me = new Me();
                me.setUsername(client.getMyUserName());
                if(loggincallback!=null){
                    loggincallback.run();
                }
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
            client.login(username,password,new LoginListenerImpl());
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
    public boolean login(Runnable callback){
        loggincallback=callback;
        return login();
    }

    @Override
    public boolean logout() {
        return false;
    }

    @Override
    public User getUserInfo() {
        return me;
    }

    private Map<String,Concat> allconcat=new HashMap<>();
    private Map<Concat,Room> concatRoomMap=new HashMap<>();
    private class GetSubscriptionListenerImpl implements GetSubscriptionListener {

        @Override
        public void onGetSubscriptions(List<SubscriptionObject> subscriptions, ErrorObject error) {
            if (error==null){
                ChatRoomFactory factory = client.getChatRoomFactory();
                for (SubscriptionObject room : subscriptions){
                    factory.addChatRoom(room);
                    if(String.valueOf(room.getRoomType())=="ONE_TO_ONE"){
                        Person person = new Person();
                        person.setNickname(room.getRoomName());
                        person.setUUID(room.getRoomId());
                        allconcat.put(room.getRoomId(),person);
                        concatRoomMap.put(person,room);
                    }else{
                        Group group=new Group();
                        group.setGroupName(room.getRoomName());
                        group.setUUID(room.getRoomId());
                        allconcat.put(room.getRoomId(),group);
                        concatRoomMap.put(group,room);
                    }

                }
            }else{
                System.out.println("Got error "+error.getMessage());
            }
            if(refreshcallback!=null)refreshcallback.run();
        }
    }

    private Runnable refreshcallback;
    public void Refresh(Runnable callback){
        refreshcallback=callback;
        //Refresh
        client.getSubscriptions(new GetSubscriptionListenerImpl());
    }
    @Override
    @NotNull
    public Concat[] getAllConcats() {
        if(!logged){
            logger.error("Want get all concats,but it not logged.");
            //todo
            return new Concat[0];//don't null
        }
        //Refresh(null);
        Concat[] res=new Concat[allconcat.size()];

        return allconcat.values().toArray(res);//don't null
    }

    @Override
    public Message[] getRecentMessage() {
        //todo
        return new Message[0];
    }


    private MessageListener messageListener;

    private class SubscriptionListenerImpl implements com.rocketchat.core.callback.MessageListener.SubscriptionListener{

        @Override
        public void onMessage(String roomId, RocketChatMessage message) {
            //todo
            System.out.println(roomId+message.getMessage());
            Message message1 = new Message(null,null,allconcat.getOrDefault(message.getRoomId(),null));
            if(messageListener!=null){
                messageListener.run(message1);
            }

        }
    }
    @Override
    public void registerListener(MessageListener messageListener) {
        //todo
        this.messageListener=messageListener;
        ChatRoomFactory factory = client.getChatRoomFactory();
        ArrayList<RocketChatAPI.ChatRoom> chatRooms = factory.getChatRooms();
        for (RocketChatAPI.ChatRoom room:chatRooms){
            room.subscribeRoomMessageEvent(new SubscribeListener() {
                @Override
                public void onSubscribe(Boolean isSubscribed, String subId) {
                    if (isSubscribed) {
                        System.out.println("subscribed to room successfully");
                    }
                }
            },new SubscriptionListenerImpl());
        }
    }

    @Override
    public void sendMessage(Message message, Callback callback) {
        //todo
    }

    @Override
    public void sendMessage(Message message) {
        //todo
        System.out.println("Send Message");
        Concat toCconcat = message.getConcat();
        ChatRoomFactory factory = client.getChatRoomFactory();
        RocketChatAPI.ChatRoom chatRoomById = factory.getChatRooms().get(0);
        System.out.println(chatRoomById.getRoomData().getRoomName());
        chatRoomById.sendMessage(message.getContant());
    }



}
