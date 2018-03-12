package scau.oop.wechat; 

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import scau.oop.wechat.msg.TextMessage;


/** 
* Test Tester. 
* 
* @author <Authors name> 
* @since <pre>03/12/2018</pre> 
* @version 1.0 
*/ 
public class TestTest { 

@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: Add(int a, int b) 
* 
*/ 
@Test
public void testAdd() throws Exception {

    scau.oop.wechat.Test t=new scau.oop.wechat.Test();
    Assert.assertEquals(3,t.Add(1,2));
    Assert.assertEquals(0,t.Add(0,0));
    Assert.assertEquals(-1,t.Add(0,-1));
    Assert.assertEquals(0,t.Add(0,0));
    Assert.assertEquals(0,t.Add(0,0));
    Assert.assertEquals(0,t.Add(0,0));
    Assert.assertEquals(0,t.Add(0,0));
    Assert.assertEquals(0,t.Add(0,0));


//TODO: Test goes here... 
} 


} 
