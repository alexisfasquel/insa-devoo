/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controller;

import Model.Area;
import Tools.Tsp.Graph;
import View.Welcome;
import java.util.Map;
import org.graphstream.graph.implementations.MultiGraph;

/**
 *
 * @author mattramo
 */
public class MainController {
    
    private Area mArea;
    
    
    public MainController(){
        mArea = new Area();
    }
   
    public MultiGraph loadPlan(String fileName) {
        
        // Donner chemin de fichier.
      mArea.loadMap(fileName);
      
      return mArea.getGraph();
        
        
    }
    
    public void loadDeliveries() {
        
    }
    
    
    
    public static void main(String[] args){
        
        MainController mController = new MainController();
        
        Welcome mWelcome = new Welcome (mController);
        
    }


}
