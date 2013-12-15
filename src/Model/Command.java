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
    

    
    protected DeliveryPoint mDeliveryPoint;
    protected Area mArea;
    protected CommandList mCommandList;
    
    abstract public void Undo(Itinary itinary,Node intersection, String idClient);
    abstract public boolean Do(Itinary itinary,Node intersection, String idClient);
        
        public Command(){
        mCommandList = new CommandList();
    }
   
          public boolean CheckIfDeliveryNode(Node node){
        DeliveryPoint dp =node.getAttribute("delivery");
        if(dp == null){
            return false;
        }
        return true;
    }
 
    
}
