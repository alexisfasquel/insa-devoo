/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controller;

import Model.Area;
import Model.Itinary;
import org.graphstream.graph.Node;

/**
 *
 * @author Eleonore
 */
public class Add extends Command {

    private Itinary mItinary;
    private String cliendId;
    
    public Add(Area area, Node selectedNode, int itinary, String clientId) {
        super(area, selectedNode);
        mItinary = mArea.getTour().get(itinary);
        cliendId = clientId;
    }
    
    public void execute() throws Area.NoTourException {
        add(mItinary, cliendId);
    }
    
    public void reverse() throws Area.NoTourException {
         deleteDelivery();
    }
    
}
