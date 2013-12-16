/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Model;

import Tools.Reader.DeliveryLoader;
import Tools.Reader.MapReader;
import Tools.Tsp.RegularGraph;
import Tools.Tsp.SolutionState;
import Tools.Tsp.TSP;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.graphstream.algorithm.AStar;
import org.graphstream.algorithm.AStar.Costs;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;
import org.graphstream.graph.Path;
import org.graphstream.graph.implementations.MultiGraph;

/**
 *
 * @author Aleks
 */


public class Area{
    
    private static final int MAX_TIME = 10000;
    private AStar mAstar;
    
    MultiGraph mGraph;
    
    private Node mWareHouse;
    private List<Itinary> mTour;
    
    private Node selected;
    
    private Stack StackUndo;
    private Stack StackRedo;
    
    public Area() {
        
        mGraph = new MultiGraph("map");
        
        mGraph.addAttribute("ui.quality");
        mGraph.addAttribute("ui.antialias");
        mGraph.addAttribute("ui.stylesheet", "url('./map_style.css')");
        
        mTour = new ArrayList<>();
        
        mAstar = new AStar(mGraph);
        
        mAstar.setCosts(new TimeCost());
    }
    
    public MultiGraph getGraph() {
        return mGraph;
    }
    
    public void setWareHouse(String wareHouseId) {
        mWareHouse = mGraph.getNode(wareHouseId);
        mWareHouse.addAttribute("warehouse");
        mWareHouse.addAttribute("ui.class", "warehouse");
    }
    
    public Itinary addItinary(Date start, Date end) {
        Itinary itinary = new Itinary(start, end, mTour.size());
        mTour.add(itinary);
        return itinary;
    }
    
    public void addDelivery(Itinary itinary, String idClient, String adress) {
        itinary.addDeliveryPoint(mGraph.getNode(adress), idClient);
    }
    
    public void loadMap(String filePath) 
            throws LoadingException {
        
        MapReader planReader = new MapReader(filePath);
        
        planReader.process();
        
        List<MapReader.Node> nodes = planReader.getNodes();
        for (int i = 0; i < nodes.size(); i++) {
            Node node = mGraph.addNode(String.valueOf(nodes.get(i).getId()));
            node.addAttribute("xy", nodes.get(i).getX(), nodes.get(i).getY());
        }
        List<MapReader.Edge> edges = planReader.getEdges();
        for (int i = 0; i < edges.size(); i++) {
            
                Edge edge = mGraph.addEdge(String.valueOf(i), String.valueOf(edges.get(i).getNodeIdL()), String.valueOf(edges.get(i).getNodeIdR()), true);
                if(edges.get(i).getweight() > 110) {
                    edge.addAttribute("ui.class", "boulevard" );    
                } else if(edges.get(i).getweight() < 80) {
                    
                } else {
                    edge.addAttribute("ui.class", "avenue" );    
                }
                edge.addAttribute("time", edges.get(i).getweight());

        }
    }
    
    public void loadDeliveries(String filePath) throws LoadingException{
        System.out.println(filePath);
        for (int i = 0; i < mTour.size(); i++) {
            mTour.get(i).removeDeliveryPoints();
        }
        mTour = new ArrayList<>();
        DeliveryLoader deliveryReader = new DeliveryLoader(filePath, this);
        deliveryReader.process();
        
    }
    
    public List<Itinary> GetItinary(){
        return mTour;
    }
    

    //TODO Transfor NullPointerException into our own exception
    public void computeRoadMap() throws NullPointerException {
        if (mTour == null) {
            throw new NullPointerException();
        }
        ArrayList<ArrayList<Integer>> succ = new ArrayList<ArrayList<Integer>>();
        
        int maxArcCost = 0;
	int minArcCost = 1000000;
        
        //Init the number of vertice and the list by computing the total of delivery point of the tour
        int nbVertices = 1; // Not forgetting the warehouse 
        succ.add(new ArrayList<Integer>()); // and as a result to add a new list
        for (int i = 0; i < mTour.size(); i++) {
            int size = mTour.get(i).getDeliveryNb();
            nbVertices += size;
            for (int j = 0; j < size; j++) {
                succ.add(new ArrayList<Integer>());
            }
        }
        
	int[][] costs = new int[nbVertices][nbVertices];
        
        Path[][] paths = new Path[nbVertices][nbVertices];
        
        //Offset to compute the index through the double loop
        int offset = 1;
        for (int i = 0; i < mTour.size(); i++) {
            
            // Getting the list of deliveryPoint of the current time frame
            List<Node> timeFrame = mTour.get(i).getDeliveries();
            
            //If we' looping over the first time frame...
            if (i == 0) {
                // then we want to add the wareHouse as a successor of each deliveryPoint
                ArrayList<Integer> succTmp = new ArrayList<>();
                for (int j = 0; j < timeFrame.size(); j++) {
                    succTmp.add(j+offset);
                    mAstar.compute(mWareHouse.getId(), timeFrame.get(j).getId());
                    Path path = mAstar.getShortestPath();
                    int cost = (int)(double)path.getPathWeight("time");
                    costs[0][j+offset] = cost;
                    paths[0][j+offset] = path;
                    if(maxArcCost < cost) {
                        maxArcCost = cost;
                    }
                    if (minArcCost > cost) {
                        minArcCost = cost;
                    }
                }
                succ.set(0, succTmp);
            }
            // Now, for each delivery point..
            for (int j = 0; j < timeFrame.size(); j++) {
                Node dp = timeFrame.get(j);
                ArrayList<Integer> succJ = succ.get(j+offset);
                // If we're at the last itinary of the tour, we'll have to add the warehouse as a succesor
                if (i == mTour.size() - 1) {
                    succJ.add(0);
                    mAstar.compute(dp.getId(), mWareHouse.getId());
                    Path path = mAstar.getShortestPath();
                    int cost = (int)(double)path.getPathWeight("time");
                    costs[j+offset][0] = cost;
                    paths[j+offset][0] = path;
                    if(maxArcCost < cost) {
                        maxArcCost = cost;
                    }
                    if (minArcCost > cost) {
                        minArcCost = cost;
                    }
                } else { // If not the last itinary...
                    // Then we add as successors each delivery point from the next itinary
                    List<Node> nextTimeFrame = mTour.get(i+1).getDeliveries();
                    for (int k = 0; k < nextTimeFrame.size(); k++) {
                        int id = k + offset + timeFrame.size();
                        succJ.add(id);
                        mAstar.compute(dp.getId(), nextTimeFrame.get(k).getId());
                        Path path = mAstar.getShortestPath();
                        int cost = (int)(double)path.getPathWeight("time");
                        costs[j+offset][id] = cost;
                        paths[j+offset][id] = path;
                        if(maxArcCost < cost) {
                            maxArcCost = cost;
                        }
                        if (minArcCost > cost) {
                            minArcCost = cost;
                        }
                    }
                }
                //And in any case, each delivery point of an itinary is a successor of the others
                for (int k = 0; k < timeFrame.size(); k++) {
                    //... Except for itself, rtfm
                    if (j != k) {
                        succJ.add(k+offset);
                        
                        mAstar.compute(dp.getId(), timeFrame.get(k).getId());
                        Path path = mAstar.getShortestPath();
                        int cost = (int)(double)path.getPathWeight("time");
                        costs[j+offset][k+offset] = cost;
                        paths[j+offset][k+offset] = path;
                        if(maxArcCost < cost) {
                            maxArcCost = cost;
                        }
                        if (minArcCost > cost) {
                            minArcCost = cost;
                        }
                    }
                }
            }
            offset += timeFrame.size();
        }
        
        
        //Computing solution
        RegularGraph g = new RegularGraph(nbVertices, minArcCost, maxArcCost, costs, succ);
        TSP solver = new TSP(g);
        SolutionState solve = solver.solve(MAX_TIME, maxArcCost*nbVertices);
        int[] next = solver.getNext();
        int[] solution = new int[next.length+1];
        
        
        
        for (int i = 0; i < next.length; i++) {
            solution[i] = i;
            solution[i+1] = next[i];
        }
        
        for (int i = 0; i < solution.length; i++) {
            System.out.println(solution[i]);
        }
        
        //Translating the solution into smth usefull
        offset = 0;
        for (int i = 0; i < mTour.size(); i++) {
            Itinary itinary = mTour.get(i);
            int size = itinary.getDeliveryNb();
            List<Path> directions = new ArrayList<>();
            for (int j = 0; j < size; j++) {
                directions.add(paths[solution[j+offset]][solution[j+offset+1]]);
            }
            if(i == mTour.size() - 1) {
                directions.add(paths[solution[nbVertices-1]][solution[nbVertices]]);
            }
            offset += size;
            itinary.setDirections(directions);
        }
        
        
    }
            
    private static class TimeCost implements Costs {

        @Override
        public double heuristic(Node node, Node node1) {
            return 0;
        }

        @Override
        public double cost(Node node, Edge edge, Node node1) {
            if(edge.getSourceNode().equals(node))
                return Integer.MAX_VALUE; 
            return edge.getAttribute("time");
        }
        
        
        
    }
    
    public List<Itinary> getTour() {
        return mTour;
    }
}
