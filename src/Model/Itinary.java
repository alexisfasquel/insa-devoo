
package Model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;
import org.graphstream.graph.Path;

/**
 * A itinary is the shorter road to follow in a scale of time
 */
public class Itinary {
    
    private final Date mStart;
    private final Date mEnd;
    
    private static final String[] COLORS = {"green", "magenta", "cyan"};
    private static final SimpleDateFormat sdf = new SimpleDateFormat("HH'h'mm");
   
    public static final int GREEN = 0;

    public static final int MAGENTA = 1;

    public static final int CYAN = 2;
    
    private List<Node> mDeliveries;
    private List<Path> mRoadMap;
    
    private final String mColor;
    
    /**
     * Create an itinary with a date of start and end, a color and a list of 
     * deliveries.
     * @param start     date of the start of the new itinary to create
     * @param end       date of the end of the new itinary to create
     * @param colorId   id of the color for this itinary
     */
    public Itinary(Date start, Date end, int colorId){
        mStart = start;
        mEnd = end;
        mDeliveries = new ArrayList<>();
        mColor = COLORS[colorId];
    }

    /**
     * @return the number of the deliveries of <code>this</code>
     */
    public int getDeliveryNb() {
        return mDeliveries.size();
    }
    
    /**
     * @return the date of the start of <code>this</code>
     */
    public Date getStart() {
        return mStart;
    }
    
    /**
     * @return the date of the end of <code>this</code>
     */
    public Date getEnd() {
        return mEnd;
    }
    
    /**
     * Create a new delivery point and add it to a list of deliveries.
     * @param intersection 
     * @param idClient     the number which is the identification of a specific client
     * @return the list of deliveries with the new delivery point added.
     */
    public boolean addDeliveryPoint(Node intersection, String idClient) {
        DeliveryPoint dp = new DeliveryPoint(idClient, this);
        intersection.setAttribute("delivery", dp);
        intersection.setAttribute("ui.class", mColor);
        return mDeliveries.add(intersection);
    }

    /**
     * Delete a delivery point of a list of deliveries.
     * @param intersection
     */
    public void removeDeliveryPoint(Node intersection) {
        mDeliveries.remove(intersection);
        intersection.removeAttribute("delivery");
        intersection.removeAttribute("ui.class");
        intersection.removeAttribute("ui.label");
    }
    
    /**
     * Remove all the delivery points
     */
    public void removeDeliveryPoints() {
        if(mRoadMap != null) {
            removeDirections();
        }
        for (int i = 0; i < mDeliveries.size(); i++) {
            mDeliveries.get(i).removeAttribute("delivery");
            mDeliveries.get(i).removeAttribute("ui.class");
            mDeliveries.get(i).removeAttribute("ui.label");
        }
        mDeliveries = new ArrayList<>();
    }

    /**
     *
     * @throws Throwable
     */
    @Override
    protected void finalize() throws Throwable {
        System.out.println("Itinary removed !");
        super.finalize(); //To change body of generated methods, choose Tools | Templates.
        
    }
    
    /**
     * @return a list of deliveries of <code>this</code>
     */
    public List<Node> getDeliveries() {
        return mDeliveries;
    }
    
    /**
     * Replace an old list of directions by a new one.
     * @param directions
     */
    public void setDirections(List<Path> directions) {
        if(mRoadMap != null) {
            removeDirections();
        }
        Calendar calendar = GregorianCalendar.getInstance(); 
        calendar.setTime(mStart);
        
        mRoadMap = directions;
        for (int i = 0; i < mRoadMap.size(); i++) {
            for (Edge edge: mRoadMap.get(i).getEachEdge()) {
                edge.addAttribute("ui.class", mColor);
            }
            Node root = mRoadMap.get(i).getRoot();
            if(root.getAttribute("warehouse") == null) {
                
                calendar.add(Calendar.SECOND, (int)(double)mRoadMap.get(i).getPathWeight("time"));
                
                String time = sdf.format(calendar.getTime());
                root.setAttribute("ui.label", time);
            }
        }
    }
    
    /*
    * Getting the directions
    */
    public String getDirections() {
        String res = "";
        for (int i = 0; i < mRoadMap.size(); i++) {
            List<Edge> edges = mRoadMap.get(i).getEdgePath();
            
            String name = (String)edges.get(0).getAttribute("name");
            res += "Prendre la rue : " + name + "\n";
            for (int j = 1; j < edges.size(); j++) {
                String nextName = (String)edges.get(j).getAttribute("name");
                if(name.equals(nextName)) {
                    continue;
                } else {
                    name = nextName;
                    res += "Prendre la rue : " + name + "\n";
                }
            }
            res += "Arrivé au point de livraison " + mRoadMap.get(i).getRoot().getId() + " : " + mRoadMap.get(i).getRoot().getAttribute("ui.label") + "\n";
        }
        return res;
    }
    
    public float getAngle(Node source, Node target) {
        float angle = 0;//(float) Math.toDegrees(Math.atan2(target..g - x, target.y - y));

        if(angle < 0){
            angle += 360;
        }

        return angle;
    }
    
    /*
    * Remove the directions
    */
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
