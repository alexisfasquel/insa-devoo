
package Controller;

import Model.Area;
import Model.LoadingException;
import View.Welcome;
import java.io.IOException;
import java.util.Stack;
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
            mDoneStack = new Stack<Command>();
            mUndoneStack = new Stack<Command>();
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
            mDoneStack = new Stack<Command>();
            mUndoneStack = new Stack<Command>();
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
            mWelcome.reload();
        } catch (Area.NoTourException ex) {
            mWelcome.displayPopup("Error", "Pas de demande de livraisons faites");
        } catch (LoadingException ex) {
            mWelcome.displayPopup("Error", ex.getMessage());
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
            mWelcome.reload();
        } catch (Area.NoTourException ex) {
            mWelcome.displayPopup("Error", "Pas de demande de livraisons faites");
        } catch (LoadingException ex) {
            mWelcome.displayPopup("Error", ex.getMessage());
        }
        mUndoneStack.push(command);
    }
    
    /**
     * Ask the area to add a deliveryPoint to the area
     * @param selected
     * @param itinary
     */
    public void addDelivery(Node selected, int itinary, String idclient){
        Add add = new Add(mArea, selected, itinary, idclient);
        try {
            add.execute();
        } catch (Area.NoTourException ex) {
            mWelcome.displayPopup("Error", "Pas de demande de livraisons faites");
        } catch (LoadingException ex) {
            mWelcome.displayPopup("Error", ex.getMessage());
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
            mWelcome.displayPopup("Error", ex.getMessage());
        }
        mDoneStack.push(delete);
    }
    
    
    public void exportTour(String path) {
        try {
            mArea.exportTour(path);
        } catch (IOException ex) {
            mWelcome.displayPopup("Error", "Le fichier demandé n'est pas trouvable");
        }
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
