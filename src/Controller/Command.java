

package Controller;

import Model.Area;
import Model.DeliveryPoint;
import Model.Itinary;
import Model.LoadingException;
import org.graphstream.graph.Node;


public abstract class Command {
    
    /**
     * 
     */
    protected Node mNode;

    /**
     *
     */
    protected Area mArea;
    
    /**
     *
     * @throws Area.NoTourException
     */
    abstract public void execute() throws Area.NoTourException, LoadingException;

    /**
     *
     * @throws Area.NoTourException
     */
    abstract public void reverse() throws Area.NoTourException, LoadingException;
        
    /**
     * @param area
     * @param selectedNode
     */
    public Command (Area area, Node selectedNode) {
        mNode = selectedNode;
        mArea = area;
    }

    /**
     *
     * @param Node
     * @return boolean
     */
    public boolean CheckIfDeliveryNode(Node node){
        DeliveryPoint dp = node.getAttribute("delivery");
        if(dp == null){
            return false;
        }
        return true;
    }

    /**
     * Add a deliveryPoint from the area
     * @param itinary
     * @param cliendId
     * @throws Area.NoTourException
     */
    public void add(Itinary itinary, String cliendId) throws LoadingException, Area.NoTourException {
        mArea.addDelivery(itinary, cliendId, mNode.getId());
        //mArea.addDelivery(itinary, intersection.getId(), idClient);
        mArea.computeRoadMapDij();//Beug dans undo de delivery
        
    }
    
    /**
     * Delete a deliveryPoint from the area
     * @throws Area.NoTourException
     */
    public void deleteDelivery() throws Area.NoTourException{
          DeliveryPoint dp = mNode.getAttribute("delivery");
          mArea.deleteDelivery(dp.getItinary(), mNode.getId());
          mArea.computeRoadMapDij();
          
    }
    
 
    
}
