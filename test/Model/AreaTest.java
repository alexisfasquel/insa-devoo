/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Model;

import java.util.Date;
import org.graphstream.graph.implementations.MultiGraph;
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
public class AreaTest {
    
    public AreaTest() {
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
     * Test of getGraph method, of class Area.
     */
    @Test
    public void testGetGraph() {
        System.out.println("getGraph");
        Area instance = new Area();
        MultiGraph expResult = null;
        MultiGraph result = instance.getGraph();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setWareHouse method, of class Area.
     */
    @Test
    public void testSetWareHouse() {
        System.out.println("setWareHouse");
        String wareHouseId = "";
        Area instance = new Area();
        instance.setWareHouse(wareHouseId);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addItinary method, of class Area.
     */
    @Test
    public void testAddItinary() {
        System.out.println("addItinary");
        Date start = null;
        Date end = null;
        Area instance = new Area();
        Itinary expResult = null;
        Itinary result = instance.addItinary(start, end);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addDelivery method, of class Area.
     */
    @Test
    public void testAddDelivery() {
        System.out.println("addDelivery");
        Itinary itinary = null;
        String idClient = "";
        String adress = "";
        Area instance = new Area();
        instance.addDelivery(itinary, idClient, adress);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of loadMap method, of class Area.
     */
    @Test
    public void testLoadMap() throws Exception {
        System.out.println("loadMap");
        String filePath = "";
        Area instance = new Area();
        instance.loadMap(filePath);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of loadDeliveries method, of class Area.
     */
    @Test
    public void testLoadDeliveries() throws Exception {
        System.out.println("loadDeliveries");
        String filePath = "";
        Area instance = new Area();
        instance.loadDeliveries(filePath);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of computeRoadMap method, of class Area.
     */
    @Test
    public void testComputeRoadMap() {
        System.out.println("computeRoadMap");
        Area instance = new Area();
        instance.computeRoadMap();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
