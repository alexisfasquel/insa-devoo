/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controller;

import Model.Add;
import Model.Area;
import Model.Itinary;
import Model.LoadingException;
import View.Welcome;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.graphstream.algorithm.Toolkit;
import org.graphstream.graph.Node;



/**
 *
 * @author mattramo
 */
public class Controller {
    
    private Area mArea;
    private Add add;
    
    public Controller(){
        mArea = new Area();
        add = new Add(mArea);
    }
    private void startApp() {
        System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
        new Welcome (this, mArea.getGraph());
    }
    
    
    public void loadPlan(String fileName) {
        try {
            // Donner chemin de fichier.
            mArea.loadMap(fileName);
        } catch (LoadingException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }
    
    public void loadDeliveries(String fileName) {
        try {
            mArea.loadDeliveries(fileName);
        } catch (LoadingException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void computeRoadMap() {
        mArea.computeRoadMap();
    }
    
    public void addDelivery(){
     Node node = Toolkit.randomNode(mArea.getGraph());
     Itinary itinéraire =mArea.GetItinary().get(0);
     add.Do(itinéraire, node, "12");
     
     
    }
    
    public static void main(String[] args){
        Controller mController = new Controller();
        mController.startApp();
    }


}
