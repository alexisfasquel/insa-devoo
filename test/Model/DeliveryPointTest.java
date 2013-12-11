/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Model;

import java.util.Date;
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
     String mClientId;
     Itinary mItinary;
     DeliveryPoint dp;
    
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
       mClientId="mClientId";
       Date date = new Date();
       mItinary=new Itinary(date, date, 0);
       dp = new DeliveryPoint(mClientId, mItinary);
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
        String expResult = "mClientId";
        String result = dp.getNclient();
        assertEquals("mClientId", dp.getNclient());

        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getItinary method, of class DeliveryPoint.
     */
    @Test
    public void testGetItinary() {
        System.out.println("getItinary");
        Itinary expResult = mItinary;
        Itinary result = dp.getItinary();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    
}
