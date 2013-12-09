/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package View;

import Model.Area;
import Model.Intersection;
import Model.Itinary;
import Model.RoadSection;
import Tools.Reader.DeliveryLoader;
import Tools.Reader.MapReader;
import java.awt.HeadlessException;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.xml.parsers.ParserConfigurationException;
import org.graphstream.algorithm.AStar;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.NodeFactory;
import org.graphstream.graph.Path;
import org.graphstream.graph.implementations.AbstractGraph;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.swingViewer.Viewer;
import org.xml.sax.SAXException;

/**
 *
 * @author Aleks
 * WILL EXTEND 
 */
public class MainWindow extends JFrame{
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
         
yep        Area a = new Area();
        a.loadMap(null);
        a.loadTour(null);
        a.computeRoadMap();
        
        
        Viewer v = a.getGraph().display(false);
        v.getDefaultView().setMouseManager(new PlanMouseManager());
        
       
        
    }

    public MainWindow() {
    }
    
}
