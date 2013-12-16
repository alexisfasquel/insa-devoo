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
    
    private List<Node> mDeliveries;
    private List<Path> mRoadMap;
    
    private final String mColor;
    
    public Itinary(Date start, Date end, int colorId){
        mStart = start;
        mEnd = end;
        mDeliveries = new ArrayList<>();
        mColor = COLORS[colorId];
    }
    
    
    public int getDeliveryNb() {
        return mDeliveries.size();
    }
    
    public Date getStart() {
        return mStart;
    }
    
    public Date getEnd() {
        return mEnd;
    }
    
    public boolean addDeliveryPoint(Node intersection, String idClient) {
        DeliveryPoint dp = new DeliveryPoint(idClient, this);
        intersection.setAttribute("delivery", dp);
        intersection.setAttribute("ui.class", mColor);
        return mDeliveries.add(intersection);
    }
    public void removeDeliveryPoint(Node intersection) {
        intersection.removeAttribute("delivery");
        intersection.removeAttribute("ui.class");
    }
    
    public void removeDeliveryPoints() {
        if(mRoadMap != null) {
            removeDirections();
        }
        for (int i = 0; i < mDeliveries.size(); i++) {
            mDeliveries.get(i).removeAttribute("delivery");
            mDeliveries.get(i).removeAttribute("ui.class");
        }
        mDeliveries = new ArrayList<>();
    }

    @Override
    protected void finalize() throws Throwable {
        System.out.println("Itinary removed !");
        super.finalize(); //To change body of generated methods, choose Tools | Templates.
        
    }
    
    
    
    public List<Node> getDeliveries() {
        return mDeliveries;
    }
    
    public void setDirections(List<Path> directions) {
        mRoadMap = directions;
        for (int i = 0; i < mRoadMap.size(); i++) {
            for (Edge edge: mRoadMap.get(i).getEachEdge()) {
                edge.addAttribute("ui.class", mColor);
            }
            Node root = mRoadMap.get(i).getRoot();
            if(root.getAttribute("warehouse") == null) {
                root.addAttribute("ui.label", "Test");
            }
        }
    }
    
    private void removeDirections() {
        
        for (int i = 0; i < mRoadMap.size(); i++) {
            for (Edge edge: mRoadMap.get(i).getEachEdge()) {
                edge.removeAttribute("ui.class");
            }
            Node root = mRoadMap.get(i).getRoot();
            if(root.getAttribute("warehouse") == null) {
                root.removeAttribute("ui.label");
            }
        }
    }
    
    
}
