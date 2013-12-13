/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;
import org.graphstream.graph.Path;

/**
 *
 * @author Aleks
 */
public class Itinary {
    
    private final Date mStart;
    private final Date mEnd;
    
    private static final String[] COLORS = {"green", "magenta", "cyan"};
    
    public static final int GREEN = 0;
    public static final int MAGENTA = 1;
    public static final int CYAN = 2;
    
    private List<Node> mDelivery;
    private List<Path> mRoadMap;
    
    private final String mColor;
    
    public Itinary(Date start, Date end, int colorId){
        mStart = start;
        mEnd = end;
        mDelivery = new ArrayList<>();
        mColor = COLORS[colorId];
    }
    
    
    public int getDeliveryNb() {
        return mDelivery.size();
    }
    
    public boolean addDeliveryPoint(Node intersection, String idClient) {
        DeliveryPoint dp = new DeliveryPoint(idClient, this);
        intersection.setAttribute("delivery", dp);
        intersection.setAttribute("ui.class", mColor);
        return mDelivery.add(intersection);
    }
    
    public List<Node> getDeliveries() {
        return mDelivery;
    }
    
    /*public void order(int[] order) {
        List<Node> tmp = new ArrayList<>();
        for (int i = 0; i < mDeliveryPoints.size(); i++) {
            tmp.add(mDeliveryPoints.get(order[i]));
        }
        mDeliveryPoints = tmp;
    }*/
    
    void setDirections(List<Path> directions) {
        mRoadMap = directions;
        for (int i = 0; i < mRoadMap.size(); i++) {
            for (Edge edge: mRoadMap.get(i).getEachEdge()) {
                edge.addAttribute("ui.class", mColor);
            }
        }
    }
    
    
}
