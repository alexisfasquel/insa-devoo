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
public class Delete extends Command{
    
    
        public Delete(Area area,CommandList commandList){
        mArea = area;
        mCommandList = commandList;
    }
    
    
      @Override
    public void Redo(){
           DeleteDelivery(mNode);
    }
    
    public void Undo(){
        AddDelivery(mDeliveryPoint.getItinary(),mNode, mDeliveryPoint.getNclient());
    }
    
}
