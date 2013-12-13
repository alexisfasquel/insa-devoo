/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package View;

import Model.Area;
import Model.LoadingException;
import javax.swing.JFrame;
import org.graphstream.ui.swingViewer.Viewer;

/**
 *
 * @author Aleks
 * WILL EXTEND 
 */
public class MainWindow extends JFrame{
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws LoadingException {

        System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
         
        Area a = new Area();
        a.loadMap(null);
        a.loadDeliveries(null);
        a.computeRoadMap();
        
        
        Viewer v = a.getGraph().display(false);
        v.getDefaultView().setMouseManager(new PlanMouseManager());
        
       
        
    }

    public MainWindow() {
    }
    
}
