/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Model;

import org.graphstream.graph.Node;

/**
 *
 * @author Eleonore
 */
public abstract class Command {
    

    
    protected Node mNode;
    protected Area mArea;
    protected CommandList mCommandList;
    protected DeliveryPoint mDeliveryPoint;
    
    abstract public void Undo();
    abstract public void Redo();
        

   
    public boolean CheckIfDeliveryNode(Node node){
        DeliveryPoint dp =node.getAttribute("delivery");
        if(dp == null){
            return false;
        }
        return true;
    }
          
      public boolean AddDelivery(Itinary itinary,Node intersection, String idClient){
        mNode = intersection;
       if( !CheckIfDeliveryNode( intersection)){
        itinary.addDeliveryPoint(intersection,idClient);
        mDeliveryPoint = intersection.getAttribute("delivery");
        mArea.computeRoadMap();//Voir si compute road map revient du debut
        mCommandList.AddComandUndo(this);
        return true;
    }else{
            return false;
            }
    }
    
    public void DeleteDelivery(Node intersection){
          DeliveryPoint dp = intersection.getAttribute("delivery");
          Itinary itinary = dp.getItinary();
          itinary.RemoveDeliveryPoint(intersection);
          mArea.computeRoadMap();
          mCommandList.AddComandRedo(this);
    }
    
 
    
}
