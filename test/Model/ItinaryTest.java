/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Cjaume
 */
public class ItinaryTest {
    
    private Date mStart;
    private Date mEnd;
    public static final int GREEN = 0;
    public static final int MAGENTA = 1;
    public static final int CYAN = 2;
    private Itinary mItinary;
    private MultiGraph mGraph;
    

        
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
       
        mStart = new Date(79);
        mEnd = new Date(80);
        mItinary = new Itinary(mStart, mEnd, 1);
        mGraph = new MultiGraph("map");

    }
    
    
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getDeliveryNb method, of class Itinary.
     * @throws java.lang.Exception
     */
    @Test
    public void testGetDeliveryNb() throws Exception{
        System.out.println("getDeliveryNb");
        int expResult = 0;
        int result = mItinary.getDeliveryNb();
        assertEquals(expResult, result);
    }

    
     /**
     * Test of getDeliveries method, of class Itinary.
     * @throws java.lang.Exception
     */
    @Test
    public void testGetDeliveries() throws Exception {
        System.out.println("getDeliveries");
        List<Node> mNode= new ArrayList();
        List<Node> result = mItinary.getDeliveries();
        assertEquals(mNode, result);
        
    }

    
    
    /**
     * Test of addDeliveryPoint method, of class Itinary.
     * @throws java.lang.Exception
     */
    @Test
    public void testAddDeliveryPoint() throws Exception{
        System.out.println("addDeliveryPoint");
        mGraph.addNode("node");
        String idClient = "3";
        boolean result = mItinary.addDeliveryPoint(mGraph.getNode("node"), idClient);
        DeliveryPoint dp = mGraph.getNode("node").getAttribute("delivery");
        assertNotNull(dp);
  
    }

 
    /**
     * Test of removeDeliveryPoint method, of class Itinary.
     */
    @Test
    public void testRemoveDeliveryPoint() {
        System.out.println("removeDeliveryPoint");
        Node node = mGraph.addNode("42");
        mItinary.addDeliveryPoint(node, "35");
        mItinary.removeDeliveryPoint(node);
        int result = mItinary.getDeliveryNb();
        int expResult=0;
        assertEquals(expResult, result);
    }

    /**
     * Test of removeDeliveryPoints method, of class Itinary.
     */
    @Test
    public void testRemoveDeliveryPoints() {
        System.out.println("removeDeliveryPoints");
        Node node1 = mGraph.addNode("41");
        Node node2 = mGraph.addNode("42");
        Node node3 = mGraph.addNode("43");
        mItinary.addDeliveryPoint(node1, "31");
        mItinary.addDeliveryPoint(node2, "32");
        mItinary.addDeliveryPoint(node3, "33");
        mItinary.removeDeliveryPoint(node1);
        mItinary.removeDeliveryPoint(node2);
        mItinary.removeDeliveryPoint(node3);
        int result = mItinary.getDeliveryNb();
        int expResult=0;
        assertEquals(expResult, result);
    }


   
}
