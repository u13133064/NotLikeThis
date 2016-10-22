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

public class AWSScannerTest {
    
    public AWSScannerTest() {
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
     * Test of scanFullNetwork method, of class AWSScanner.
     */
    @Test
    public void testScanFullNetwork() {
        System.out.println("scanFullNetwork");
        AWSScanner instance = null;
        instance.scanFullNetwork();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of scanRegion method, of class AWSScanner.
     */
    @Test
    public void testScanRegion() {
        System.out.println("scanRegion");
        String region = "";
        AWSScanner instance = null;
        instance.scanRegion(region);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of scanNetworkFrom method, of class AWSScanner.
     */
    @Test
    public void testScanNetworkFrom() {
        System.out.println("scanNetworkFrom");
        String level = "";
        String identifier = "";
        AWSScanner instance = null;
        instance.scanNetworkFrom(level, identifier);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of run method, of class AWSScanner.
     */
    @Test
    public void testRun() {
        System.out.println("run");
        AWSScanner instance = null;
        instance.run();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
