/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controller;

import Model.Area;
import Model.LoadingException;
import View.Welcome;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author mattramo
 */
public class Controller {
    
    private Area mArea;
    
    public Controller(){
        mArea = new Area();
    }
    private void startApp() {
        System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
        new Welcome (this, mArea.getGraph(), mArea);
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
    
    
    public static void main(String[] args){
        Controller mController = new Controller();
        mController.startApp();
    }


}
