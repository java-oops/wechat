package scau.oop.wechat.backend.msg; 

import org.junit.Test; 
import org.junit.Before; 
import org.junit.After;
import scau.oop.wechat.backend.RocketBackend;

/** 
* RocketBackend Tester. 
* 
* @author <Authors name> 
* @since <pre>05/12/2018</pre> 
* @version 1.0 
*/ 
public class RocketBackendTest { 

@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: call() 
* 
*/ 
@Test
public void testCall() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: onConnect(String sessionID) 
* 
*/ 
@Test
public void testOnConnect() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: onDisconnect(boolean closedByServer) 
* 
*/ 
@Test
public void testOnDisconnect() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: onConnectError(Exception websocketException) 
* 
*/ 
@Test
public void testOnConnectError() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: onLogin(TokenObject token, ErrorObject error) 
* 
*/ 
@Test
public void testOnLogin() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: login() 
* 
*/ 
@Test
public void testLogin() throws Exception { 
//TODO: Test goes here...
    new RocketBackend().login();
} 

/** 
* 
* Method: logout() 
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
* Method: getAllConcats() 
* 
*/ 
@Test
public void testGetAllConcats() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: getRecentMessage() 
* 
*/ 
@Test
public void testGetRecentMessage() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: registerListener(Listener listener) 
* 
*/ 
@Test
public void testRegisterListener() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: sendMessage(Message message, Callback callback) 
* 
*/ 
@Test
public void testSendMessageForMessageCallback() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: sendMessage(Message message) 
* 
*/ 
@Test
public void testSendMessageMessage() throws Exception { 
//TODO: Test goes here... 
} 

public static void main(String[] args){
    new RocketBackend().login();
}

} 
