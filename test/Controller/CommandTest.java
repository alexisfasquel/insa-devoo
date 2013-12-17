/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controller;

import Model.Area;
import Model.Itinary;
import org.graphstream.graph.Node;
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
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of execute method, of class Command.
     */
    @Test
    public void testExecute() throws Exception {
        System.out.println("execute");
        
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of reverse method, of class Command.
     */
    @Test
    public void testReverse() throws Exception {
        System.out.println("reverse");
        
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of CheckIfDeliveryNode method, of class Command.
     */
    @Test
    public void testCheckIfDeliveryNode() {
        System.out.println("CheckIfDeliveryNode");
        /*Node node = null;
        Command instance = null;
        boolean expResult = false;
        boolean result = instance.CheckIfDeliveryNode(node);
        assertEquals(expResult, result);*/
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of add method, of class Command.
     */
    @Test
    public void testAdd() throws Exception {
        System.out.println("add");
        /*Itinary itinary = null;
        String cliendId = "";
        Command instance = null;
        instance.add(itinary, cliendId);*/
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteDelivery method, of class Command.
     */
    @Test
    public void testDeleteDelivery() throws Exception {
        System.out.println("deleteDelivery");
        /*Command instance = null;
        instance.deleteDelivery();*/
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    public class CommandImpl extends Command {

        public CommandImpl() {
            super(null, null);
        }

        public void execute() throws Area.NoTourException, Area.AlreadyComputedException {
        }

        public void reverse() throws Area.NoTourException, Area.AlreadyComputedException {
        }
    }
    
}
