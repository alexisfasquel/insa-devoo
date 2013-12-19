/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controller;

import Model.Area;
import Model.DeliveryPoint;
import Model.Itinary;
import org.graphstream.graph.Node;

/**
 *
 * @author Eleonore
 */
public abstract class Command {
    
    protected Node mNode;
    protected Area mArea;
    
    abstract public void execute() throws Area.NoTourException;
    abstract public void reverse() throws Area.NoTourException;
        
    public Command (Area area, Node selectedNode) {
        mNode = selectedNode;
        mArea = area;
    }

   
    public boolean CheckIfDeliveryNode(Node node){
        DeliveryPoint dp = node.getAttribute("delivery");
        if(dp == null){
            return false;
        }
        return true;
    }
          
      public void add(Itinary itinary, String cliendId) throws Area.NoTourException {
        mArea.addDelivery(itinary, cliendId, mNode.getId());
        //mArea.addDelivery(itinary, intersection.getId(), idClient);
        mArea.computeRoadMapDij();//Beug dans undo de delivery
        
    }
    
    public void deleteDelivery() throws Area.NoTourException{
          DeliveryPoint dp = mNode.getAttribute("delivery");
          mArea.deleteDelivery(dp.getItinary(), mNode.getId());
          mArea.computeRoadMapDij();
          
    }
    
 
    
}
