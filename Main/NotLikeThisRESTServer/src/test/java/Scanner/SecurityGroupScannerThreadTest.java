/*
This is a placeholder file 
These test were completely manually
This file is for possible future testing
 */
package Scanner;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class SecurityGroupScannerThreadTest {
    
    public SecurityGroupScannerThreadTest() {
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
     * Test of scanOnly method, of class SecurityGroupScannerThread.
     */
    @Test
    public void testScanOnly() {
        System.out.println("scanOnly");
        SecurityGroupScannerThread instance = null;
        instance.scanOnly();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of scanContext method, of class SecurityGroupScannerThread.
     */
    @Test
    public void testScanContext() {
        System.out.println("scanContext");
        SecurityGroupScannerThread instance = null;
        instance.scanContext();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of run method, of class SecurityGroupScannerThread.
     */
    @Test
    public void testRun() {
        System.out.println("run");
        SecurityGroupScannerThread instance = null;
        instance.run();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
