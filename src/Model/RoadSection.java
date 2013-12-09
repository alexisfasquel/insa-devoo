/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Model;

import org.graphstream.graph.implementations.AbstractEdge;
import org.graphstream.graph.implementations.AbstractNode;

/**
 *
 * @author Aleks
 */



public class RoadSection extends AbstractEdge{
    
    public static final float CONVERSION_FACTOR = 3.6f;
    private int mSpeed;
    private int mDistance;
    private int mTime;
    
    protected RoadSection(String id, AbstractNode source, AbstractNode target, boolean directed, int speed, int distance) {
        super(id, source, target, directed);
        mSpeed = speed;
        mDistance = distance;
        
    }
}
