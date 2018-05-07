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
import java.lang.Thread;
import static java.lang.Thread.sleep;

/** 
* Service Tester. 
* 
* @author <Authors name> 
* @since <pre>03/12/2018</pre> 
* @version 1.0 
*/ 
public class ServiceTest {

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
 * Method: testWxInit
 *
 */
@Test
public void testWxInit() throws Exception {
//TODO: Test goes here...
        }

/** 
* 
* Method: testWxStatusNotify()
* 
*/ 
@Test
public void testWxStatusNotify() throws Exception {
//TODO: Test goes here... 
} 


/** 
* 
* Method: syncCheck()
* 心跳检查
*/ 
@Test
public void testSyncCheck() throws Exception {
//TODO: Test goes here... 
} 

/** 
* 
* Method: getContact()
* 获取联系人列表
*/ 
@Test
public void testGetContact() throws Exception {
//TODO: Test goes here...


}


/**
*
* Method: testWebwxsync()
*
*/
@Test
public void testWebwxsync() throws Exception {
//TODO: Test goes here...

}


    /**
     *
     * Method: testWebwxsendmsg()
     *
     */
    @Test
    public void testWebwxsendmsg() throws Exception {
//TODO: Test goes here...

    }

} 
