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
import org.graphstream.graph.Path;

/**
 *
 * @author Aleks
 */
public class Itinary {
    
    private Date mStart;
    private Date mEnd;
    
    private static final String[] COLORS = {"green", "magenta", "cyan"};
    
    public static final int GREEN = 0;
    public static final int MAGENTA = 1;
    public static final int CYAN = 2;
    
    private List<DeliveryPoint> mDeliveryPoints;
    
    private String mColor;
    
    public Itinary(Date start, Date end, int colorId){
        mStart = start;
        mEnd = end;
        mDeliveryPoints = new ArrayList<>();
        mColor = COLORS[colorId];
    }
    
    public int getDeliveryNb() {
        return mDeliveryPoints.size();
    }
    
    public boolean addDeliveryPoint(DeliveryPoint dp) {
        return mDeliveryPoints.add(dp);
    }
    
    public List<DeliveryPoint> getDeliveryPoints() {
        return mDeliveryPoints;
    }
    
    public void order(int[] order) {
        List<DeliveryPoint> tmp = new ArrayList<>();
        for (int i = 0; i < mDeliveryPoints.size(); i++) {
            tmp.add(mDeliveryPoints.get(order[i]));
        }
        mDeliveryPoints = tmp;
    }
    
    void addDirections(Path[] directions) {
        for (int i = 0; i < mDeliveryPoints.size(); i++) {
            List<Edge> direction = directions[i].getEdgePath();
            for (int j = 0; j < direction.size(); j++) {
                direction.get(j).setAttribute("ui.class", mColor);
            }
            mDeliveryPoints.get(i).setDirection(directions[i]);
        }
    }
    
}
