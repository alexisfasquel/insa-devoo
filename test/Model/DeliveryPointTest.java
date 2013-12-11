/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Model;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author admin
 */
public class DeliveryPointTest {
    
    public DeliveryPointTest() {
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
     * Test of getNclient method, of class DeliveryPoint.
     */
    @Test
    public void testGetNclient() {
        System.out.println("getNclient");
        DeliveryPoint instance = null;
        String expResult = "";
        String result = instance.getNclient();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getItinary method, of class DeliveryPoint.
     */
    @Test
    public void testGetItinary() {
        System.out.println("getItinary");
        DeliveryPoint instance = null;
        Itinary expResult = null;
        Itinary result = instance.getItinary();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
