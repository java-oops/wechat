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
    static String wxid = null;
    static String wxuin = null;
    static String pass_ticket = null;

@Before
public void before() throws Exception { 
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

    final long l = System.currentTimeMillis();//返回当前的计算机时间

    String res = Http.post("http://login.weixin.qq.com/jslogin",
            new HashMap<String, Object>(){{
                put("appid","wx782c26e4c19acffb");
                put("fun","new");
                put("_",l);
            }});

    System.out.println("获取uuid: "+res);

    Pattern p = Pattern.compile("window.QRLogin.uuid = \"(.+)\"");
    Matcher m = p.matcher(res);
    while(m.find()) {
        uuid= m.group(1);
    }

    System.out.println("得到的uuid: "+uuid);

    Http.downloadFile("http://login.weixin.qq.com/qrcode/"+uuid,"1.png"); //使用上面得到的uuid请求二维码图像

    //java使用Runtime.getRuntime().exec()可以在windows中调用系统命令：
    //Runtime.getRuntime().exec("cmd /c E:\\美图秀秀\\XiuXiu\\XiuXiu.exe 1.png");

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
    //}
    System.out.println("检测登录状态："+stateCode);

        while(!stateCode.equals("200")) {
            if(stateCode.equals("201")) {
                System.out.println("二维码已被扫描");
                res = Http.waitPost("http://login.weixin.qq.com/cgi-bin/mmwebwx-bin/login", new HashMap<String, Object>() {{
                    put("tip", "0");
                    put("uuid", uuid);
                    put("_", l);
                }});
                m = p.matcher(res);
                while (m.find()) {
                    stateCode = m.group(1);
                }
                System.out.println("检测用户是否确认登录："+stateCode);
            } else {
                System.out.println("登录超时，重新加载二维码");
                System.out.println("登录超时的状态码："+stateCode);
                testLogin();
                break;
            }

        }

        if(stateCode.equals("200")) {
            System.out.println("登录成功");
            System.out.println("res: "+res);
            m = Pattern.compile("ticket=(.+)&uuid").matcher(res);
            while (m.find()) {
               ticket = m.group(1);
            }
            System.out.println("ticket: "+ticket);
            m = Pattern.compile("scan=(.+)\"\\;").matcher(res);
            while (m.find()) {
                scan = m.group(1);
            }
            System.out.println("scan: "+scan);
            res = Http.waitPost("http://wx.qq.com/cgi-bin/mmwebwx-bin/webwxnewloginpage", new HashMap<String, Object>() {{
                put("ticket", ticket);
                put("uuid", uuid);
                put("lang", "zh_CN");
                put("scan", scan);
                put("fun", "new");
            }});
            System.out.println("获取cookie");

            m = Pattern.compile("<skey>(.+)</skey>").matcher(res);
            while (m.find()) {
                skey = m.group(1);
            }
            System.out.println("skey: "+skey);

            m = Pattern.compile("<wxid>(.+)</wxid>").matcher(res);
            while (m.find()) {
                wxid = m.group(1);
            }
            System.out.println("wxid: "+wxid);

            m = Pattern.compile("<wxuin>(.+)</wxuin>").matcher(res);
            while (m.find()) {
                wxuin = m.group(1);
            }
            System.out.println("wxuin: "+wxuin);

            m = Pattern.compile("<pass_ticket>(.+)</pass_ticket>").matcher(res);
            while (m.find()) {
                pass_ticket = m.group(1);
            }
            System.out.println("pass_ticket: "+pass_ticket);
        }
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
