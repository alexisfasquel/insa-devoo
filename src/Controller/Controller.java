/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controller;

import Model.Area;
import Model.LoadingException;
import View.Welcome;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.graphstream.graph.Node;


/**
 *
 * @author mattramo
 */
public class Controller {
    
    private Area mArea;
    private Welcome mWelcome;
    private Stack<Command> mDoneStack;
    private Stack<Command> mUndoneStack;
    
    public Controller(){
        mArea = new Area();
        mUndoneStack = new Stack();
        mDoneStack = new Stack();
    }
    
    private void startApp() {
        System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");

        mWelcome = new Welcome (this, mArea.getGraph(), mArea);
        
        //loadPlan("plan.xml");
        //loadDeliveries("livraison.xml");
        //computeRoadMap();
    }
    
    public void loadPlan(String fileName) {
        try {
            // Donner chemin de fichier.
            mArea.loadMap(fileName);
        } catch (LoadingException ex) {
            
            mWelcome.displayPopup("Error", ex.getMessage());
        }  
    }
    
    public void loadDeliveries(String fileName) {
        try {
            //mArea.dijkstra();
            mArea.loadDeliveries(fileName);
        } catch (LoadingException ex) {
              
           mWelcome.displayPopup("Error", ex.getMessage());
        }
    }
    
    public void computeRoadMap() {
        try {
            mArea.computeRoadMapDij();
        } catch (Area.NoTourException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            mWelcome.displayPopup("Error", "Pas de demande de livraisons faites");
        }
    }
    
    public void reDo() {
        Command command = mUndoneStack.pop();
        try {
            command.execute();
        } catch (Area.NoTourException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        mDoneStack.push(command);
    }
    
    public void unDo() {
        Command command = mDoneStack.pop();
        try {
            command.reverse();
        } catch (Area.NoTourException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        mUndoneStack.push(command);
    }
    
    public void addDelivery(Node selected, int itinary){
        Add add = new Add(mArea, selected, itinary, "tet");
        try {
            add.execute();
        } catch (Area.NoTourException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        mDoneStack.push(add);
        
     }
    
    public boolean CheckUndo(){
        return !mDoneStack.empty();
    }
    
    public boolean CheckRedo(){
        return !mUndoneStack.empty();
    }
    
    public void DeleteDelivery(Node selected){
        Delete delete = new Delete(mArea, selected);
        try {
            delete.execute();
        } catch (Area.NoTourException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        mDoneStack.push(delete);
    }
    
    public static void main(String[] args){
        Controller mController = new Controller();
        mController.startApp();
    }
}
