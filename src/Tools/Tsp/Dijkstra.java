
package Tools.Tsp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;
import org.graphstream.graph.Path;

/**
 *
 * Description of the class
 */
public class Dijkstra {

    private static Set<Node> settledNodes;
    private static Set<Node> unSettledNodes;
    private static Map<Node, Node> predecessors;
    private static Map<Node, Double> distance;

    private Dijkstra() {}

    /**
     *
     * @param source
     * @param destination
     * @return
     */
    public static Path execute(Node source, Node destination) {
        settledNodes = new HashSet<>();
        unSettledNodes = new HashSet<>();
        distance = new HashMap<>();
        predecessors = new HashMap<>();
        distance.put(source, 0.0);
        unSettledNodes.add(source);
        while (unSettledNodes.size() > 0) {
            Node node = getMinimum(unSettledNodes);
            settledNodes.add(node);
            unSettledNodes.remove(node);
            findMinimalDistances(node);
            if (node.equals(destination)) {
                break;
            }
        }
        return getPath(destination);
    }

    private static void findMinimalDistances(Node node) {
        List<Node> adjacentNodes = getNeighbors(node);
        for (Node target : adjacentNodes) {
            if (getShortestDistance(target) > getShortestDistance(node) + getDistance(node, target)) {
                distance.put(target, getShortestDistance(node) + getDistance(node, target));
                predecessors.put(target, node);
                unSettledNodes.add(target);
            }
        }

    }

    private static Double getDistance(Node node, Node target) {
        Edge edge = node.getEdgeToward(target);
        return edge.getAttribute("time");
    }

    private static List<Node> getNeighbors(Node node) {
        Iterator<Node> neighbors = node.getNeighborNodeIterator();
        List<Node> neighborhood = new ArrayList<>();
        while (neighbors.hasNext()) {
            Node neighbor = neighbors.next();
            neighborhood.add(neighbor);
        }
        return neighborhood;
    }

    private static Node getMinimum(Set<Node> vertexes) {
        Node minimum = null;
        for (Node vertex : vertexes) {
            if (minimum == null) {
                minimum = vertex;
            } else {
                if (getShortestDistance(vertex) < getShortestDistance(minimum)) {
                    minimum = vertex;
                }
            }
        }
        return minimum;
    }

    private static Double getShortestDistance(Node destination) {
        Double d = distance.get(destination);
        if (d == null) {
            return Double.MAX_VALUE;
        } else {
            return d;
        }
    }

    /*
     * This method returns the path from the source to the selected target and
     * NULL if no path exists   */
    private static Path getPath(Node target) {
        Path path = new Path();
        Node step = target;
        // check if a path exists
        if (predecessors.get(step) == null) {
            return null;
        }
        path.add(step, step.getEdgeFrom(predecessors.get(step)));
        while (predecessors.get(predecessors.get(step)) != null) {
            step = predecessors.get(step);
            path.add(step, step.getEdgeBetween(predecessors.get(step)));
        }
    // Put it into the correct order
        //Collections.reverse(path);

        return path;
    }

}
