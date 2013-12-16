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
    
    public Add(Area area){
        mArea = area;
    }
    
    public boolean Do(Itinary itinary,Node intersection, String idClient){
       if( !CheckIfDeliveryNode( intersection)){
        itinary.addDeliveryPoint(intersection,idClient);
        mArea.computeRoadMap();
        mCommandList.AddComandUndo(this);
        return true;
    }else{
            return false;
            }
    }
    
    public void Undo(Itinary itinary, Node intersection, String idClient){
          itinary.removeDeliveryPoint(intersection);
          mArea.computeRoadMap();
          mCommandList.RemoveComandUndo();
          mCommandList.AddComandRedo(this);
    }

  
    
}
