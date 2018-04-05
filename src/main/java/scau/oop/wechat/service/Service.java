package scau.oop.wechat.service;

import scau.oop.wechat.chatroom.ChatRoom;
import scau.oop.wechat.chatroom.Person;
import scau.oop.wechat.msg.Message;
import java.util.concurrent.Callable;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author:czfshine
 * @date:2018/3/12 14:54
 */

public class Service {

    static String uuid;
    static String ticket = null;
    static String scan = null;
    static String skey = null;
    static String wxid = null;
    static String wxuin = null;
    static String pass_ticket = null;

    public void Login(){
        final long l = System.currentTimeMillis();//返回当前的计算机时间

        String res = Http.post("http://login.weixin.qq.com/jslogin",
                new HashMap<String, Object>(){{
                    put("appid","wx782c26e4c19acffb");
                    put("fun","new");
                    put("_",l);
                }});

        Pattern p = Pattern.compile("window.QRLogin.uuid = \"(.+)\"");
        Matcher m = p.matcher(res);
        while(m.find()) {
            uuid= m.group(1);
        }


        Http.downloadFile("http://login.weixin.qq.com/qrcode/"+uuid,"1.png"); //使用上面得到的uuid请求二维码图像


        String  stateCode = null;
        res = Http.waitPost("http://login.weixin.qq.com/cgi-bin/mmwebwx-bin/login", new HashMap<String, Object>() {{ //检查二维码扫描状态
            put("tip", "1");
            put("uuid", uuid);
            put("_", l);
        }});
        p = Pattern.compile("window.code=(.+)\\;");
        m = p.matcher(res);
        while (m.find()) {
            stateCode = m.group(1);
        }

        while(!stateCode.equals("200")) {
            if(stateCode.equals("201")) {
                res = Http.waitPost("http://login.weixin.qq.com/cgi-bin/mmwebwx-bin/login", new HashMap<String, Object>() {{
                    put("tip", "0");
                    put("uuid", uuid);
                    put("_", l);
                }});
                m = p.matcher(res);
                while (m.find()) {
                    stateCode = m.group(1);
                }
            } else {
                Login();
                break;
            }

        }

        if(stateCode.equals("200")) {
            m = Pattern.compile("ticket=(.+)&uuid").matcher(res);
            while (m.find()) {
                ticket = m.group(1);
            }
            m = Pattern.compile("scan=(.+)\"\\;").matcher(res);
            while (m.find()) {
                scan = m.group(1);
            }
            res = Http.waitPost("http://wx.qq.com/cgi-bin/mmwebwx-bin/webwxnewloginpage", new HashMap<String, Object>() {{
                put("ticket", ticket);
                put("uuid", uuid);
                put("lang", "zh_CN");
                put("scan", scan);
                put("fun", "new");
            }});

            m = Pattern.compile("<skey>(.+)</skey>").matcher(res);
            while (m.find()) {
                skey = m.group(1);
            }

            m = Pattern.compile("<wxid>(.+)</wxid>").matcher(res);
            while (m.find()) {
                wxid = m.group(1);
            }

            m = Pattern.compile("<wxuin>(.+)</wxuin>").matcher(res);
            while (m.find()) {
                wxuin = m.group(1);
            }

            m = Pattern.compile("<pass_ticket>(.+)</pass_ticket>").matcher(res);
            while (m.find()) {
                pass_ticket = m.group(1);
            }
        }
    }

    public void Logout(){

    }
    public void getUserInfo(){

    }

    public Message[] getMessages(){

        return null;
    }

    public void listen(Callable<Message> callback){

    }
    public boolean postMessage(Message msg){
        return false;
    }
    public String getImage(Message msg){
        return null;
    }
    public String getVideo(Message msg){
        return null;
    }
    public ChatRoom[] getAllChatRoom(){
        return null;
    }
    public Person[] getAllPerson(){
        return null;
    }


}
