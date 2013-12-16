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
public class Add extends Command {
    
    public Add(Area area,CommandList commandList){
        mArea = area;
        mCommandList = commandList;
    }
    
    @Override
    public void Redo(){
        AddDelivery(mDeliveryPoint.getItinary(),mNode, mDeliveryPoint.getNclient());
    }
    
    public void Undo(){
         DeleteDelivery(mNode);
    }

   

  
    
}
