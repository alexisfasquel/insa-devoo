/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.graphstream.algorithm.AStar;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;
import org.xml.sax.Attributes;
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
public class AreaTest {
    private MultiGraph mGraph;
    private ArrayList<Object> mTour;
    private AStar mAstar;
    Area areaT;
    private Node mWareHouse;
    private Attributes attributes;
    
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
        areaT = new Area();
        mGraph = new MultiGraph("map");
        mGraph.addAttribute("ui.quality");
        mGraph.addAttribute("ui.antialias");
        mGraph.addAttribute("ui.stylesheet", "url('./map_style.css')");
        mTour = new ArrayList<>(1);
        mAstar = new AStar(mGraph);

    }
    
    @After
    public void tearDown() {
    }
    
    /**
     * Test of addItinary method, of class Area.
     * @throws java.lang.Exception
     */
    @Test
    public void testAddItinary() throws Exception{
        System.out.println("addItinary");
        Date start = new Date(79); 
        Date end = new Date(80);
        Itinary itinary = areaT.addItinary(start, end);
        Itinary mItinary = new Itinary(start, end, mTour.size());
        assertEquals(mItinary, itinary );    

    }

    /**
     * Test of addDelivery method, of class Area.
     */
    @Test
    public void testAddDelivery() {
       // go to see Test of addDeliveryPoint, of class Itinary
    }

    /**
     * Test of loadMap method, of class Area.
     * @throws java.lang.Exception
     */
    @Test
    public void testLoadMap() throws Exception {
        System.out.println("loadMap");
        
        // case 1 : Correct file
        String filePath = "plan.xml";
        areaT.loadMap(filePath);
        
        
        
    }
    
     /**
     * Test of loadMap method, of class Area.
     * @throws java.lang.Exception
     */
    @Test
    public void testLoadMapNoFile() throws Exception {
        System.out.println("loadMapNoFile");
        
        String filePath = "null.xml";
        
        Throwable caught = null;
        try {
           areaT.loadMap(filePath);
        } catch (LoadingException t) {
           caught = t;
        }
        assertNotNull(caught);
        assertSame(LoadingException.class, caught.getClass());
        
    }
    
    /**
     * Test of loadMap method, of class Area.
     * @throws java.lang.Exception
     */
    @Test
    public void testLoadMapSyntax() throws Exception {
        System.out.println("loadMapSyntax");
        

         // case 3 : Syntaxic Error
        String filePath = "testFiles/planSyntax.xml";
        Throwable caught = null;
        try {
           areaT.loadMap(filePath);
        } catch (LoadingException t) {
           caught = t;
        }
        assertNotNull(caught);
        assertSame(LoadingException.class, caught.getClass());
        

        
        
    }
    
    /**
     * Test of loadMap method, of class Area.
     * @throws java.lang.Exception
     */
    @Test
    public void testLoadMapSemantic() throws Exception {
        System.out.println("loadMapSemantic");
        

        // case 4 : Semantic Error
        String filePath = "testFiles/planSemantic.xml";
        
        Throwable caught = null;
        try {
           areaT.loadMap(filePath);
        } catch (LoadingException t) {
           caught = t;
        }
        assertNotNull(caught);
        assertSame(LoadingException.class, caught.getClass());
        
        
    }
    

    
        /**
     * Test of loadMap method, of class Area.
     * @throws java.lang.Exception
     */
    @Test
    public void testLoadMapNegativeNb() throws Exception {
        System.out.println("loadMapNegativeNb");
        
         // case 5 : Negative number
        String filePath = "testFiles/planNegativeNb.xml";
        
        Throwable caught = null;
        try {
           areaT.loadMap(filePath);
        } catch (LoadingException t) {
           caught = t;
        }
        assertNotNull(caught);
        assertSame(LoadingException.class, caught.getClass());
        
        
    }
    
 


    /**
     * Test of loadDeliveries method, of class Area.
     * @throws java.lang.Exception
     */
    @Test
    public void testLoadDeliveriesWithoutPlan() throws Exception {
        System.out.println("loadDeliveriesWithoutPlan");
        
        //Chargement livraison sans avoir chargé de plan
        String fileDeliveries = "null.xml";
        
        Throwable caught = null;
        try {
           areaT.loadDeliveries(fileDeliveries);
        } catch (LoadingException t) {
           caught = t;
        }
        assertNotNull(caught);
        assertSame(LoadingException.class, caught.getClass());

    }
    
     /**
     * Test of loadDeliveries method, of class Area.
     * @throws java.lang.Exception
     */
    @Test
    public void testLoadDeliveries() throws Exception {
        System.out.println("loadDeliveries");
        
        //Cas normal
        String filePlan = "testFiles/planTest.xml";
        areaT.loadMap(filePlan);
        String fileDeliveries = "testFiles/livraisonTest.xml";
        areaT.loadDeliveries(fileDeliveries);

    }
    
    /**
     * Test of loadDeliveries method, of class Area.
     * @throws java.lang.Exception
     */
    @Test
    public void testLoadDeliveriesNoFile() throws Exception {
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");

    }
    
    /**
     * Test of loadDeliveries method, of class Area.
     * @throws java.lang.Exception
     */
    @Test
    public void testLoadDeliveriesSemantic() throws Exception {
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");

    }

    /**
     * Test of loadDeliveries method, of class Area.
     * @throws java.lang.Exception
     */
    @Test
    public void testLoadDeliveriesSyntax() throws Exception {
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");

    }
    
    /**
     * Test of loadDeliveries method, of class Area.
     * @throws java.lang.Exception
     */
    @Test
    public void testLoadDeliveriesNodeNotInPlan() throws Exception {
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");

    }
    
    /**
     * Test of computeRoadMap method, of class Area.
     * @throws java.lang.Exception
     */
    @Test
    public void testComputeRoadMap() throws Exception {
        System.out.println("computeRoadMap");
        Area instance = new Area();
        instance.loadMap("testFiles/planTest.xml");
        instance.loadDeliveries("testFiles/livraisonTest.xml");
        instance.computeRoadMap();
     
    }

    /**
     * Test of getGraph method, of class Area.
     */
    @Test
    public void testGetGraph() {
        System.out.println("getGraph");
        /*Area instance = new Area();
        MultiGraph expResult = null;
        MultiGraph result = instance.getGraph();
        assertEquals(expResult, result);*/
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setWareHouse method, of class Area.
     */
    @Test
    public void testSetWareHouse() {
        System.out.println("setWareHouse");
        /*String wareHouseId = "";
        Area instance = new Area();
        instance.setWareHouse(wareHouseId);*/
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteDelivery method, of class Area.
     */
    @Test
    public void testDeleteDelivery() {
        System.out.println("deleteDelivery");
        /*Itinary itinary = null;
        String adress = "";
        Area instance = new Area();
        instance.deleteDelivery(itinary, adress);*/
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of GetItinary method, of class Area.
     */
    @Test
    public void testGetItinary() {
        System.out.println("GetItinary");
        /*Area instance = new Area();
        List<Itinary> expResult = null;
        List<Itinary> result = instance.GetItinary();
        assertEquals(expResult, result);*/
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTour method, of class Area.
     */
    @Test
    public void testGetTour() {
        System.out.println("getTour");
        /*Area instance = new Area();
        List<Itinary> expResult = null;
        List<Itinary> result = instance.getTour();
        assertEquals(expResult, result);*/
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
