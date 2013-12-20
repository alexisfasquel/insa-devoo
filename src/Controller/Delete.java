
package Controller;

import Model.Area;
import Model.DeliveryPoint;
import Model.LoadingException;
import org.graphstream.graph.Node;

public class Delete extends Command{

    private DeliveryPoint mDeliveryPoint;
    
    /**
     *
     * @param area
     * @param selectedNode
     */
    public Delete(Area area, Node selectedNode) {
        super(area, selectedNode);
        mDeliveryPoint = selectedNode.getAttribute("delivery");
    }
    
    @Override
    public void execute() throws Area.NoTourException{
           deleteDelivery();
    }
    
    @Override
    public void reverse() throws Area.NoTourException, LoadingException {
        add(mDeliveryPoint.getItinary(), mDeliveryPoint.getNclient());
    }
    
}
