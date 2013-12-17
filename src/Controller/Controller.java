/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controller;

import Model.Area;
import Model.Add;
import Model.Area;
import Model.CommandList;
import Model.Delete;
import Model.DeliveryPoint;
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
    private Delete delete;
    private CommandList  mCommandList;
    private Node CurrentNodeSelected;
    
    public Controller(){
        mArea = new Area();
        mCommandList= new CommandList();
     }

    public void setCurrentNodeSelected(Node CurrentNodeSelected) {
        this.CurrentNodeSelected = CurrentNodeSelected;
    }
    
    private void startApp() {
        System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");

        new Welcome (this, mArea.getGraph(), mArea);
        //loadPlan("plan.xml");
        //loadDeliveries("livraison.xml");
        //computeRoadMap();
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
    
    public void reDo() {
        mCommandList.Redo();
    }
    
    public void unDo() {
        mCommandList.Undo();
    }
    
    public void addDelivery( ){
        add = new Add(mArea,mCommandList);
        Itinary itinéraire =mArea.GetItinary().get(0);
        add.Do(itinéraire, CurrentNodeSelected, "12");
        CheckUndo();
     }
    public boolean CheckUndo(){
        if(!mCommandList.getStackUndo().empty()){
          return true;  
        }
        else{
            return false;  
        }
    }
    
        public boolean CheckRedo(){
        if(!mCommandList.getStackRedo().empty()){
          return true;  
        }
        else{
            return false;  
        }
    }
    public void DeleteDelivery(){
          delete = new Delete(mArea,mCommandList);
          DeliveryPoint dp = CurrentNodeSelected.getAttribute("delivery");
          delete.Do( CurrentNodeSelected);
           CheckUndo();
    }
    
    public static void main(String[] args){
        Controller mController = new Controller();
        mController.startApp();
    }
    
    public Node getCurrentNode(){
        return CurrentNodeSelected;
    }

}
