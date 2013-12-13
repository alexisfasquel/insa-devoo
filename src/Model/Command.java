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
    abstract public void Do(Itinary itinary,Node intersection, String idClient);
        
    
   
    public Command(){
        
    }
    
}
