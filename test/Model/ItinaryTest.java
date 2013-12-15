/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Model;

import java.util.List;
import org.graphstream.graph.Node;
import org.graphstream.graph.Path;
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
public class ItinaryTest {
    
    public ItinaryTest() {
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
     * Test of getDeliveryNb method, of class Itinary.
     */
    @Test
    public void testGetDeliveryNb() {
        System.out.println("getDeliveryNb");
        Itinary instance = null;
        int expResult = 0;
        int result = instance.getDeliveryNb();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addDeliveryPoint method, of class Itinary.
     */
    @Test
    public void testAddDeliveryPoint() {
        System.out.println("addDeliveryPoint");
        Node intersection = null;
        String idClient = "";
        Itinary instance = null;
        boolean expResult = false;
        boolean result = instance.addDeliveryPoint(intersection, idClient);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getDeliveries method, of class Itinary.
     */
    @Test
    public void testGetDeliveries() {
        System.out.println("getDeliveries");
        Itinary instance = null;
        List<Node> expResult = null;
        List<Node> result = instance.getDeliveries();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setDirections method, of class Itinary.
     */
    @Test
    public void testSetDirections() {
        System.out.println("setDirections");
        List<Path> directions = null;
        Itinary instance = null;
        instance.setDirections(directions);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
