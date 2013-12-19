/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controller;

import Model.Area;
import Model.DeliveryPoint;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.graphstream.graph.Node;

/**
 *
 * @author Eleonore
 */
public class Delete extends Command{

    private DeliveryPoint mDeliveryPoint;
    
    public Delete(Area area, Node selectedNode) {
        super(area, selectedNode);
        mDeliveryPoint = selectedNode.getAttribute("delivery");
    }
    
    @Override
    public void execute() throws Area.NoTourException{
           deleteDelivery();
    }
    
    @Override
    public void reverse() throws Area.NoTourException {
        add(mDeliveryPoint.getItinary(), mDeliveryPoint.getNclient());
    }
    
}
