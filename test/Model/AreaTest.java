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
 * @author Cjaume
 */
public class AreaTest {
    private MultiGraph mGraph;
    Area areaT;
    
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
        Itinary itinaryExp = areaT.addItinary(start, end);
        Itinary itinary = areaT.getTour().get(0);
        assertEquals(itinaryExp, itinary );    

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
    public void testLoadMapNodeWithoutEdge() throws Exception {
        System.out.println("loadMapNodeWithoutEdge");
        
        String filePath = "testFiles/planNodeWithoutEdge.xml";
        
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
    public void testLoadMapNodeNotInPlan() throws Exception {
        System.out.println("loadMapNodeWithoutEdge");
        
        String filePath = "testFiles/planNodeNotInPlan.xml";
        
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
    public void testLoadMapNegativeSpeed() throws Exception {
        System.out.println("loadMapNegativeSpeed");
        
        String filePath = "testFiles/planNegativeSpeed.xml";
        
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
    public void testLoadMapNullSpeed() throws Exception {
        System.out.println("loadMapNullSpeed");
        
        String filePath = "testFiles/planNullSpeed.xml";
        
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
    public void testLoadMapNullDistance() throws Exception {
        System.out.println("loadMapNullDistance");
        
        String filePath = "testFiles/planNullDistance.xml";
        
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
    public void testLoadMapNegativeDistance() throws Exception {
        System.out.println("loadMapNegativeDistance");
        
        String filePath = "testFiles/planNegativeDistance.xml";
        
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
    public void testLoadMapOnlyEdgeOut() throws Exception {
        System.out.println("loadMapNegativeDistance");

        String filePath = "testFiles/planOnlyEdgeOut.xml";
        
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
    public void testLoadDeliveriesWrongTimeScale() throws Exception {
        System.out.println("loadDeliveriesWrongTimeScale");
        
        String filePlan = "testFiles/planTest.xml";
        areaT.loadMap(filePlan);
        
        String filePath = "testFiles/livraisonWrongTimeScale.xml";
       
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
    public void testLoadDeliveriesNegativeAdress() throws Exception {
        System.out.println("loadDeliveriesNegativeAdress");
        
        String filePlan = "testFiles/planTest.xml";
        areaT.loadMap(filePlan);
        
        String filePath = "testFiles/livraisonNegativeAdress.xml";
       
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
    public void testLoadDeliveriesNoFile() throws Exception {
        System.out.println("loadDeliveriesNoFile");
        
        String filePlan = "testFiles/planTest.xml";
        areaT.loadMap(filePlan);
        
        String filePath = "null.xml";
        
        Throwable caught = null;
        try {
           areaT.loadDeliveries(filePath);
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
    public void testLoadDeliveriesSyntax() throws Exception {
        System.out.println("loadDeliveriesSyntax");
        
        String filePlan = "testFiles/planTest.xml";
        areaT.loadMap(filePlan);

        String filePath = "testFiles/livraisonSyntax.xml";
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
    public void testLoadDeliveriesNodeNotInPlan() throws Exception {
        System.out.println("loadDeliveriesNodeNotInPlan");
        
        String filePlan = "testFiles/planTest.xml";
        areaT.loadMap(filePlan);

        String filePath = "testFiles/livraisonNodeNotInPlan.xml";
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
     * Test of deleteDelivery method, of class Area.
     */
    @Test
    public void testDeleteDelivery() {
        // go to see Test of deleteDelivery, of class Itinary
    }

    
}
