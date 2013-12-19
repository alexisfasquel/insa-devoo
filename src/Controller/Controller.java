
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
 */
public class Controller {
    
    private Area mArea;
    private Welcome mWelcome;
    private Stack<Command> mDoneStack;
    private Stack<Command> mUndoneStack;
    
    /**
     * Create a controller
     */
    public Controller(){
        mArea = new Area();
        mUndoneStack = new Stack();
        mDoneStack = new Stack();
    }
    
    /*
    * Start the application
    */
    private void startApp() {
        System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");

        mWelcome = new Welcome (this, mArea.getGraph(), mArea);
        
        //loadPlan("plan.xml");
        //loadDeliveries("livraison.xml");
        //computeRoadMap();
    }
    
    /**
     * Give the path to the map file to load.
     * @param fileName name of the map file to load
     */
    public void loadPlan(String fileName) {
        try {
            // Donner chemin de fichier.
            mArea.loadMap(fileName);
        } catch (LoadingException ex) {
            
            mWelcome.displayPopup("Error", ex.getMessage());
        }  
    }
    
    /**
     * Give the path to the deliveries file to load.
     * @param fileName name of the deliveries file to load
     */
    public void loadDeliveries(String fileName) {
        try {
            //mArea.dijkstra();
            mArea.loadDeliveries(fileName);
        } catch (LoadingException ex) {
              
           mWelcome.displayPopup("Error", ex.getMessage());
        }
    }
    
    /**
     * Ask to the area to compute the shortest road
     */
    public void computeRoadMap() {
        try {
            mArea.computeRoadMapDij();
        } catch (Area.NoTourException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            mWelcome.displayPopup("Error", "Pas de demande de livraisons faites");
        }
    }
    
    /**
     * Redo the last commande made by the user
     */
    public void reDo() {
        Command command = mUndoneStack.pop();
        try {
            command.execute();
        } catch (Area.NoTourException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        mDoneStack.push(command);
    }
    
    /**
     * Undo the last commande made by the user
     */
    public void unDo() {
        Command command = mDoneStack.pop();
        try {
            command.reverse();
        } catch (Area.NoTourException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        mUndoneStack.push(command);
    }
    
    /**
     * Ask the area to add a deliveryPoint to the area
     * @param selected
     * @param itinary
     */
    public void addDelivery(Node selected, int itinary){
        Add add = new Add(mArea, selected, itinary, "tet");
        try {
            add.execute();
        } catch (Area.NoTourException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        mDoneStack.push(add);
        
     }
    
    /**
     * @return true if the done stack isn't empty.
     */
    public boolean CheckUndo(){
        return !mDoneStack.empty();
    }
    
    /**
     * @return true if the undone stack isn't empty.
     */
    public boolean CheckRedo(){
        return !mUndoneStack.empty();
    }
    
    /**
     * Ask the area to delete a deliveryPoint from the area
     * @param selected
     */
    public void DeleteDelivery(Node selected){
        Delete delete = new Delete(mArea, selected);
        try {
            delete.execute();
        } catch (Area.NoTourException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        mDoneStack.push(delete);
    }
    
    /**
     *
     * @param args
     */
    public static void main(String[] args){
        Controller mController = new Controller();
        mController.startApp();
    }
}
