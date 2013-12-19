

package Controller;

import Model.Area;
import Model.DeliveryPoint;
import Model.Itinary;
import org.graphstream.graph.Node;

/**
 *
 * Description of the class
 */
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
    abstract public void execute() throws Area.NoTourException;

    /**
     *
     * @throws Area.NoTourException
     */
    abstract public void reverse() throws Area.NoTourException;
        
    /**
     *
     * @param area
     * @param selectedNode
     */
    public Command (Area area, Node selectedNode) {
        mNode = selectedNode;
        mArea = area;
    }

    /**
     *
     * @param node
     * @return
     */
    public boolean CheckIfDeliveryNode(Node node){
        DeliveryPoint dp = node.getAttribute("delivery");
        if(dp == null){
            return false;
        }
        return true;
    }

    /**
     *
     * @param itinary
     * @param cliendId
     * @throws Area.NoTourException
     */
    public void add(Itinary itinary, String cliendId) throws Area.NoTourException {
        mArea.addDelivery(itinary, cliendId, mNode.getId());
        //mArea.addDelivery(itinary, intersection.getId(), idClient);
        mArea.computeRoadMapDij();//Beug dans undo de delivery
        
    }
    
    /**
     *
     * @throws Area.NoTourException
     */
    public void deleteDelivery() throws Area.NoTourException{
          DeliveryPoint dp = mNode.getAttribute("delivery");
          mArea.deleteDelivery(dp.getItinary(), mNode.getId());
          mArea.computeRoadMapDij();
          
    }
    
 
    
}
