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
    
    public void Do(Itinary itinary,Node intersection, String idClient){
          itinary.RemoveDeliveryPoint(intersection, idClient);
          mArea.computeRoadMap();
          mCommandList.AddComandRedo(this);
    }
    
    public void Undo(Itinary itinary,Node intersection, String idClient){
                itinary.addDeliveryPoint(intersection,idClient);
                mArea.computeRoadMap();
                mCommandList.RemoveComandUndo();
                mCommandList.AddComandRedo(this);
    }
    
}
