package scau.oop.wechat.service; 

import org.junit.Test; 
import org.junit.Before; 
import org.junit.After;
import scau.oop.wechat.msg.Message;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
import scau.oop.wechat.service.Service;

import static java.lang.Thread.sleep;

/** 
* Service Tester. 
* 
* @author <Authors name> 
* @since <pre>03/12/2018</pre> 
* @version 1.0 
*/ 
public class ServiceTest {
    static String uuid;
    static String ticket = null;
    static String scan = null;
    static String skey = null;
    static String wxsid = null;
    static String wxuin = null;
    static String pass_ticket = null;
    private static Service mockService;
    static String User = null;
    static String SyncKey = null;


@Before
public void before() throws Exception {
    //mockService = mock(Service.class);

} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: Login() 
* 
*/ 
@Test
public void testLogin() throws Exception { 
//TODO: Test goes here...
    //verify(mockService, times(1)).Login();

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
            testLogin();
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
    //System.out.println("返回的skey: "+skey);
    //System.out.println("返回的wxsid: "+wxsid);
    //System.out.println("返回的wxuin: "+wxuin);
   // System.out.println("返回的pass_ticket: "+pass_ticket);


    //微信初始化
    String BaseRequest = "\"BaseRequest\":{\"Uin\":\""+wxuin+"\",\"Sid\":\""+wxsid+"\",\"Skey\":\""+skey+"\",\"DeviceID\":\"e825563802462384\"}";

    //ServiceTest用的是main下面的Http
    res = Http.sendPost("https://wx.qq.com/cgi-bin/mmwebwx-bin/webwxinit?r="+l+"&lang=zh_CN&pass_ticket="+pass_ticket,"{"+BaseRequest+"}");

    m = Pattern.compile("\"SyncKey\":(.+)\"User\":").matcher(res);
    while (m.find()) {
        SyncKey = m.group(1);
    }
   // System.out.println("SyncKey = "+SyncKey);

    m = Pattern.compile("\"User\":(.+)\"ChatSet\":").matcher(res);
    while (m.find()) {
        User = m.group(1);
    }
    System.out.println("User = "+User);

    //把res结果打印出来
    PrintWriter outputStream = null;
    outputStream = new PrintWriter(new
            FileOutputStream("stuff.txt"));
    outputStream.println(res);
    outputStream.close();


    //开启微信状态通知
    String FromUserName = null;
    m = Pattern.compile("\"UserName\": \"(.+)\",\"NickName\"").matcher(User);
    while (m.find()) {
        FromUserName = m.group(1);
    }
    System.out.println("FromUserName = "+FromUserName);
    String wxStatusNotify = "{"+BaseRequest+",\"ClientMsgId\":\""+l+"\",\"Code\":3,\"FromUserName\":\""+FromUserName+"\",\"ToUserName\":\""+FromUserName+"\"}";
    res = Http.sendPost("https://wx.qq.com/cgi-bin/mmwebwx-bin/webwxstatusnotify?lang=zh_CN&pass_ticket="+pass_ticket,wxStatusNotify);
    System.out.println("开启状态通知的res= "+res);
}

/**
 *
 * Method: Logout()
 *
 */
@Test
public void testLogout() throws Exception {
//TODO: Test goes here...
        }

/**
 *
 * Method: getUserInfo()
 *
 */
@Test
public void testGetUserInfo() throws Exception {
//TODO: Test goes here...
        }

/** 
* 
* Method: getMessages() 
* 
*/ 
@Test
public void testGetMessages() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: Listen(Runnable callback) 
* 
*/ 
@Test
public void testListen() throws Exception { 
//TODO: Test goes here...


} 

/** 
* 
* Method: getImage(Message msg) 
* 
*/ 
@Test
public void testGetImage() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: getVideo(Message msg) 
* 
*/ 
@Test
public void testGetVideo() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: getAllChatRoom() 
* 
*/ 
@Test
public void testGetAllChatRoom() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: getAllPerson() 
* 
*/ 
@Test
public void testGetAllPerson() throws Exception { 
//TODO: Test goes here... 
} 


} 
