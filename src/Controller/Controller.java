/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controller;

import Model.Area;
import Model.DeliveryPoint;
import Model.Itinary;
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
    private Node CurrentNodeSelected;
    
    public Controller(){
        mArea = new Area();
        mUndoneStack = new Stack();
        mDoneStack = new Stack();
    }

    public void setCurrentNodeSelected(Node CurrentNodeSelected) {
        this.CurrentNodeSelected = CurrentNodeSelected;
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
            mArea.loadDeliveries(fileName);
        } catch (LoadingException ex) {
            mWelcome.displayPopup("Error", ex.getMessage());
        }
    }
    
    public void computeRoadMap() {
        try {
            mArea.computeRoadMap();
        } catch (Area.NoTourException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Area.AlreadyComputedException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void reDo() {
        Command command = mUndoneStack.pop();
        try {
            command.execute();
        } catch (Area.NoTourException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Area.AlreadyComputedException ex) {
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
        } catch (Area.AlreadyComputedException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        mUndoneStack.push(command);
    }
    
    public void addDelivery(int itinary){
        Add add = new Add(mArea, CurrentNodeSelected, itinary, "tet");
        try {
            add.execute();
        } catch (Area.NoTourException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Area.AlreadyComputedException ex) {
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
    public void DeleteDelivery(){
        Delete delete = new Delete(mArea, CurrentNodeSelected);
        try {
            delete.execute();
        } catch (Area.AlreadyComputedException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
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
