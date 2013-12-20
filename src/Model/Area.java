package Model;

import Tools.Reader.DeliveryLoader;
import Tools.Reader.MapReader;
import Tools.Tsp.Dijkstra;
import Tools.Tsp.RegularGraph;
import Tools.Tsp.RegularGraph1;
import Tools.Tsp.SolutionState;
import Tools.Tsp.TSP;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.graphstream.algorithm.AStar;
import org.graphstream.algorithm.AStar.Costs;
import org.graphstream.graph.Edge;
import org.graphstream.graph.EdgeRejectedException;
import org.graphstream.graph.ElementNotFoundException;
import org.graphstream.graph.IdAlreadyInUseException;
import org.graphstream.graph.Node;
import org.graphstream.graph.Path;
import org.graphstream.graph.implementations.SingleGraph;

/**
 * Area where deliveries can be made by a delivery person
 */
public class Area{
    
    private static final int MAX_TIME = 10000;
    private AStar mAstar;
    
    private boolean mMapLoaded = false;
    
    SingleGraph mGraph;
    
    private Node mWareHouse;
    private List<Itinary> mTour;

    /**
     * Create a area and initialize his attributes
     */
    public Area() {
        
        mGraph = new SingleGraph("map");
        mGraph.addAttribute("ui.quality");
        mGraph.addAttribute("ui.antialias");
        mGraph.addAttribute("ui.stylesheet", "url('./map_style.css')");
        
        mTour = new ArrayList<>();
        mAstar = new AStar(mGraph);
        mAstar.setCosts(new TimeCost());
    }
    
    /**
     * @return a SingleGraph of <code>this</code>
     */
    public SingleGraph getGraph() {
        return mGraph;
    }
    
    /**
     * Change the parameter wareHouseId
     * @param wareHouseId 
     */
    public void setWareHouse(String wareHouseId) {
        if(mWareHouse != null) {
            mWareHouse.removeAttribute("warehouse");
            mWareHouse.removeAttribute("ui.class");   
        }
        mWareHouse = mGraph.getNode(wareHouseId);
        mWareHouse.addAttribute("warehouse");
        mWareHouse.addAttribute("ui.class", "warehouse");
    }
    
    /**
     * Create a new itineray and add it to the liste of itinary mTour.
     * @param start date of the start of the new itinary to add
     * @param end   date of the end of the new itinary to add
     * @return the new itinary which have been created and added to mTour
     */
    public Itinary addItinary(Date start, Date end) {
        Itinary itinary = new Itinary(start, end, mTour.size());
        mTour.add(itinary);
        return itinary;
    }
    
    /**
     * Add a delivery point to an itinary.
     * @param idClient the number which is the identification of a specific client
     * @param itinary  the list of the deliveries with a start and an end
     * @param adress   the adress of the delivery to add
     * @throws Model.LoadingException
     */
    public void addDelivery(Itinary itinary, String idClient, String adress) throws LoadingException {
        Node node = mGraph.getNode(adress);
        if((node == null)) {
            throw new LoadingException("Pas de d'intersection à l'adresse spécifiée");
        }
        itinary.addDeliveryPoint(mGraph.getNode(adress), idClient);
    }

    /**
     * Delete a delivery point to an itinary. Warning when this delivery point is 
     * the last of the itinary.
     * @param itinary  the list of the deliveries with a start and an end
     * @param adress   the adress of the delivery to add
     */
    public void deleteDelivery(Itinary itinary, String adress) {
        itinary.removeDeliveryPoint(mGraph.getNode(adress));
        if(itinary.getDeliveries().isEmpty()) {
            mTour.remove(itinary);
            
        }
    }
    
    /**
     * Load a map into the application.
     * @param filePath            the path to the xml file of a plan to load
     * @throws LoadingException   when the xml file doesn't exist,
     * the numbers are negatives, there are syntaxic or semantic errors
     */
    public void loadMap(String filePath) 
            throws LoadingException {
        
        
        MapReader mapReader = new MapReader(filePath);
        
        mapReader.process();
        
        List<MapReader.Node> nodes = mapReader.getNodes();
        for (int i = 0; i < nodes.size(); i++) {
            try {
                Node node = mGraph.addNode(String.valueOf(nodes.get(i).getId()));
                node.addAttribute("xy", nodes.get(i).getX(), nodes.get(i).getY());
            } catch(IdAlreadyInUseException e) {
                
                throw new LoadingException("Deux intersections ont la même adresse...");
            }
        }
        List<MapReader.Edge> edges = mapReader.getEdges();
        for (int i = 0; i < edges.size(); i++) {
                Edge edge;
                try {
                    edge = mGraph.addEdge(String.valueOf(i), String.valueOf(edges.get(i).getNodeIdL()), String.valueOf(edges.get(i).getNodeIdR()), true);
                } catch (ElementNotFoundException e) {
                   
                    throw new LoadingException("Erreur de chargement du plan. \nUne route est attachée a une intersection inexistante...");
                } catch(EdgeRejectedException e) {
                   
                    throw new LoadingException("Erreur de chargement du plan. \nPlus d'une route est attaché entre deux intersections...");
                } 
                edge.addAttribute("name", edges.get(i).getName());
                edge.addAttribute("time", edges.get(i).getweight());
                if(edges.get(i).getweight() > 90) {
                    
                } else if(edges.get(i).getweight() < 60) {
                    edge.addAttribute("ui.class", "boulevard" ); 
                    
                } else {
                    edge.addAttribute("ui.class", "avenue" );
                }
        }
        for (Node node : mGraph.getNodeSet()) {
            if( node.getEnteringEdgeSet().isEmpty() || node.getLeavingEdgeSet().isEmpty()) {
                throw new LoadingException("Erreur de chargement du plan. \n Une intersection est depourvu de accès...");
            }
        }
        mMapLoaded = true;
    }
    
    /**
     * Load a list of deliveries into the application.
     * @param filePath            the path to the xml file of a plan to load
     * @throws LoadingException   when the xml file doesn't exist,
     * the numbers are negatives, there are syntaxic or semantic errors or the file
     * of deliveries doesn't correspond to the plan loaded before.
     */
   
    public void loadDeliveries(String filePath) throws LoadingException{
        if(!mMapLoaded) {
            throw new LoadingException("Erreur de chargement des livraions : \nPas de plan chargé...");
        }
        for (int i = 0; i < mTour.size(); i++) {
            mTour.get(i).removeDeliveryPoints();
        }
        mTour = new ArrayList<>();
        DeliveryLoader deliveryReader = new DeliveryLoader(filePath, this);
        deliveryReader.process();
        
    }
    
    /**
     * @return a list of itinary of <code>this</code>
     */
    public List<Itinary> GetItinary(){
        return mTour;
    }
    
    
    //TODO Transfor NullPointerException into our own exception

    /**
     * Compute the shorter road to follow
     * @throws NoTourException
     */
        public void computeRoadMap() throws NoTourException {
        if (mTour == null) {
            throw new NoTourException();
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

    /**
     * Compute the shorter road to follow
     * @throws NoTourException
     */
    public void computeRoadMapDij() throws NoTourException {
        
        if (mTour.isEmpty() == true) {
            throw new NoTourException();
        }
        
        //Computing the number of vertices
        
        int nbVertices = 1;     // Not forgetting the warehouse 
        for (int i = 0; i < mTour.size(); i++) {
            int size = mTour.get(i).getDeliveryNb();
            nbVertices += size;
        }
        
        //Building the needed TSP graph
        
        RegularGraph1 TspGraph = new RegularGraph1(nbVertices);
        Path[][] paths = new Path[nbVertices][nbVertices];
        
        int offset = 1;     //Offset to compute the index through the double loop
        for (int i = 0; i < mTour.size(); i++) {
            
            // Getting the list of deliveryPoint of the current time frame
            List<Node> timeFrame = mTour.get(i).getDeliveries();
            
            // Now, for each delivery point..
            for (int j = 0; j < timeFrame.size(); j++) {
                Node delivery = timeFrame.get(j);
                int target = j+offset;
                
                // If we're looping over the last time frame
                if (i == mTour.size() - 1) { 
                    // We'll have to add the warehouse as a succesor
                    Path p = Dijkstra.execute(delivery, mWareHouse);
                    paths[target][0] = p;
                    TspGraph.addSucc(target, 0);
                    TspGraph.addCost(target, 0, (int)(double)p.getPathWeight("time"));
                    
                } else if (mTour.size() > 1) {      // Not the last ?
                    // More than one itinary ? 
                    if(mTour.size() > 1) {     
                        //Then we add as successors each delivery point from the next itinary
                        List<Node> nextTimeFrame = mTour.get(i+1).getDeliveries();
                        for (int k = 0; k < nextTimeFrame.size(); k++) {
                            int id = k + offset + timeFrame.size();
                            Path p = Dijkstra.execute(delivery, nextTimeFrame.get(k));
                            paths[target][id] = p;
                            TspGraph.addSucc(target, id);
                            TspGraph.addCost(target, id, (int)(double)p.getPathWeight("time"));
                        }
                    } 
                }
                // If we're looping over the first time frame...
                if(i == 0) {    // then we want to add the delivery as a successor of the warehouse
                    Path p = Dijkstra.execute(mWareHouse, delivery);
                    paths[0][target] = p;
                    TspGraph.addSucc(0, target);
                    TspGraph.addCost(0, target, (int)(double)p.getPathWeight("time"));
                }
                
                // Then, each delivery point of an itinary is a successor of the others
                for (int k = 0; k < timeFrame.size(); k++) {
                    int succ = k+offset;
                    //... Except for itself, rtfm
                    if (j != k) {
                        Path p = Dijkstra.execute(delivery, timeFrame.get(k));
                        paths[target][succ] = p;
                        TspGraph.addSucc(target, succ);
                        TspGraph.addCost(target, succ, (int)(double)p.getPathWeight("time"));
                    }
                }
            }
            offset += timeFrame.size();
        }
        
        //Computing solution
        
        TSP solver = new TSP(TspGraph);
        SolutionState solve = solver.solve(MAX_TIME, TspGraph.getMaxArcCost()*nbVertices);
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
            System.out.println(itinary.getDirections());
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
    
    /**
     * @return a list of itinary of <code>this</code> //ATTENTION meme focntion que GetItinary non ??
     */
    public List<Itinary> getTour() {
        return mTour;
    }
    
    public class NoTourException extends Exception {}
}
