package scau.oop.wechat.service;

import scau.oop.wechat.chatroom.ChatRoom;
import scau.oop.wechat.chatroom.Person;
import scau.oop.wechat.msg.Message;
import java.io.*;
import java.io.FileOutputStream;
import java.io.PrintWriter;
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
    static String wxsid = null;
    static String wxuin = null;
    static String pass_ticket = null;
    static String User = null;
    static String SyncKey = null;

    public void Login(){
        final long l = System.currentTimeMillis();//返回当前的计算机时间

        String res = Http.post("https://login.weixin.qq.com/jslogin",
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


        Http.downloadFile("https://login.weixin.qq.com/qrcode/"+uuid,"1.png"); //使用上面得到的uuid请求二维码图像


        String  stateCode = null;
        res = Http.waitPost("https://login.weixin.qq.com/cgi-bin/mmwebwx-bin/login", new HashMap<String, Object>() {{ //检查二维码扫描状态
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
                res = Http.waitPost("https://login.weixin.qq.com/cgi-bin/mmwebwx-bin/login", new HashMap<String, Object>() {{
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
            res = Http.waitPost("https://wx.qq.com/cgi-bin/mmwebwx-bin/webwxnewloginpage", new HashMap<String, Object>() {{
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

            m = Pattern.compile("<wxsid>(.+)</wxsid>").matcher(res);
            while (m.find()) {
                wxsid = m.group(1);
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

        //微信初始化
        String BaseRequest = "\"BaseRequest\":{\"Uin\":\""+wxuin+"\",\"Sid\":\""+wxsid+"\",\"Skey\":\""+skey+"\",\"DeviceID\":\"e825563802462384\"}";
         try {
             res = Http.sendPost("https://wx.qq.com/cgi-bin/mmwebwx-bin/webwxinit?r=" + l + "&lang=zh_CN&pass_ticket=" + pass_ticket, "{" + BaseRequest + "}");
         } catch (Exception e) {
             System.out.println("发送 POST 请求出现异常！"+e);
             e.printStackTrace();
         }
        m = Pattern.compile("\"SyncKey\":(.+)\"User\":").matcher(res);
        while (m.find()) {
            SyncKey = m.group(1);
        }

        m = Pattern.compile("\"User\":(.+)\"ChatSet\":").matcher(res);
        while (m.find()) {
            User = m.group(1);
        }


        //开启微信状态通知
        String FromUserName = null;
        m = Pattern.compile("\"UserName\": \"(.+)\",\"NickName\"").matcher(User);
        while (m.find()) {
            FromUserName = m.group(1);
        }
        System.out.println("FromUserName = "+FromUserName);
        String wxStatusNotify = "{"+BaseRequest+",\"ClientMsgId\":\""+l+"\",\"Code\":3,\"FromUserName\":\""+FromUserName+"\",\"ToUserName\":\""+FromUserName+"\"}";
        try {
            Http.sendPost("https://wx.qq.com/cgi-bin/mmwebwx-bin/webwxstatusnotify?lang=zh_CN&pass_ticket=" + pass_ticket, wxStatusNotify);
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
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
