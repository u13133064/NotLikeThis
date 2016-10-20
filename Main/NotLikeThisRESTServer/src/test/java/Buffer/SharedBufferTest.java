/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Buffer;

import Composite.NetworkTree;
import SecurityGroups.SecurityRuleSet;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Daniel King
 */
public class SharedBufferTest {
    
    public SharedBufferTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of constructTree method, of class SharedBuffer.
     */
    @Test
    public void testConstructTree() {
        System.out.println("constructTree");
        SharedBuffer instance = new SharedBuffer();
        instance.constructTree();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addToBuffer method, of class SharedBuffer.
     */
    @Test
    public void testAddToBuffer() {
        System.out.println("addToBuffer");
        NetworkTree tree = null;
        SharedBuffer instance = new SharedBuffer();
        instance.addToBuffer(tree);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addToSecurityGroups method, of class SharedBuffer.
     */
    @Test
    public void testAddToSecurityGroups() {
        System.out.println("addToSecurityGroups");
        String id = "";
        SecurityRuleSet securityRuleSet = null;
        SharedBuffer instance = new SharedBuffer();
        instance.addToSecurityGroups(id, securityRuleSet);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getJSONList method, of class SharedBuffer.
     */
    @Test
    public void testGetJSONList() {
        System.out.println("getJSONList");
        SharedBuffer instance = new SharedBuffer();
        String expResult = "";
        String result = instance.getJSONList();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getInformation method, of class SharedBuffer.
     */
    @Test
    public void testGetInformation() {
        System.out.println("getInformation");
        String uuid = "";
        SharedBuffer instance = new SharedBuffer();
        String expResult = "";
        String result = instance.getInformation(uuid);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getConnections method, of class SharedBuffer.
     */
    @Test
    public void testGetConnections() {
        System.out.println("getConnections");
        String uuid = "";
        String otherID = "";
        SharedBuffer instance = new SharedBuffer();
        String expResult = "";
        String result = instance.getConnections(uuid, otherID);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of removeRoot method, of class SharedBuffer.
     */
    @Test
    public void testRemoveRoot() {
        System.out.println("removeRoot");
        SharedBuffer instance = new SharedBuffer();
        instance.removeRoot();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of stopThreads method, of class SharedBuffer.
     */
    @Test
    public void testStopThreads() {
        System.out.println("stopThreads");
        SharedBuffer instance = new SharedBuffer();
        instance.stopThreads();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of pauseThreads method, of class SharedBuffer.
     */
    @Test
    public void testPauseThreads() {
        System.out.println("pauseThreads");
        SharedBuffer instance = new SharedBuffer();
        instance.pauseThreads();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of resumeThreads method, of class SharedBuffer.
     */
    @Test
    public void testResumeThreads() {
        System.out.println("resumeThreads");
        SharedBuffer instance = new SharedBuffer();
        instance.resumeThreads();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getState method, of class SharedBuffer.
     */
    @Test
    public void testGetState() {
        System.out.println("getState");
        SharedBuffer instance = new SharedBuffer();
        int expResult = 0;
        int result = instance.getState();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getThreadNotifier method, of class SharedBuffer.
     */
    @Test
    public void testGetThreadNotifier() {
        System.out.println("getThreadNotifier");
        SharedBuffer instance = new SharedBuffer();
        Integer expResult = null;
        Integer result = instance.getThreadNotifier();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of connect method, of class SharedBuffer.
     */
    @Test
    public void testConnect() {
        System.out.println("connect");
        SharedBuffer instance = new SharedBuffer();
        instance.connect();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of disconnect method, of class SharedBuffer.
     */
    @Test
    public void testDisconnect() {
        System.out.println("disconnect");
        SharedBuffer instance = new SharedBuffer();
        instance.disconnect();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
