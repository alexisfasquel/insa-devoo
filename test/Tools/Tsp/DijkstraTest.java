/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Tools.Tsp;

import Controller.Controller;
import java.util.ArrayList;
import java.util.List;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;
import org.graphstream.graph.Path;
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
public class DijkstraTest {
    Dijkstra dijkstra;
    private List<Node> nodes;
    private List<Edge> edges;
    MultiGraph graph;
    Controller controller;
  
    public DijkstraTest() {
        controller= new Controller();

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
     * Test of execute method, of class Dijkstra.
     */
    @Test
    public void testExecute() {
        System.out.println("Dijkstra");
        nodes = new ArrayList<>();
        edges = new ArrayList<>();
        controller.loadPlan("planTestDijkstra.xml");
        
        dijkstra.execute(graph.getNode("0"));
        Path dijkstraPath = dijkstra.getPath(nodes.get(10));
        
        Path path = new Path();
        for (int i = 0; i < 11; i++) {
            path.add(graph.getNode(Integer.toString(i)), graph.getNode(Integer.toString(i)).getEdgeBetween(graph.getNode(Integer.toString(i+1))));
        }
        assertEquals(dijkstraPath, path);
        
    }

  
    
}
