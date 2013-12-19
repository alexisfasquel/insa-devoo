/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controller;

import Model.Area;
import Model.Itinary;
import java.util.Date;
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
 * @author admin
 */
public class CommandTest {
    private Date mStart;
    private Date mEnd;
    private Add mAdd;
    private Delete mDelete;  
    private Area mArea;
    
    public CommandTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        mArea = new Area();
        mArea.addItinary(mStart, mEnd);
        mArea.getGraph().addNode("delivery");
        mArea.GetItinary().get(0).addDeliveryPoint(mArea.getGraph().getNode("delivery"), "client");
        mArea.getGraph().addNode("node");
        mAdd= new Add(mArea, mArea.getGraph().getNode("delivery"), 0, "clientId");
    }
    
    @After
    public void tearDown() {
    }

   
    /**
     * Test of CheckIfDeliveryNode method, of class Command.
     */
    @Test
    public void testCheckIfDeliveryNode() {
        System.out.println("CheckIfDeliveryNode");
        
        
        boolean result = mAdd.CheckIfDeliveryNode(mArea.getGraph().getNode("delivery"));
        boolean expResult=true;
        assertEquals(expResult, result );   
    }
    
    /**
     * Test of CheckIfDeliveryNode method, of class Command.
     */
    @Test
    public void testCheckIfDeliveryNodeFalse() {
        System.out.println("CheckIfDeliveryNodeFalse");
        boolean result = mAdd.CheckIfDeliveryNode(mArea.getGraph().getNode("node"));
        boolean expResult=false;
        assertEquals(expResult, result);   
    }

    

   
    
}
