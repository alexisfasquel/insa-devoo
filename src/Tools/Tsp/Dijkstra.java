/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Tools.Tsp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;
import org.graphstream.graph.Path;
import org.graphstream.graph.implementations.MultiGraph;

/**
 *
 * @author admin
 */
public class Dijkstra {
    private final List<Node> nodes;
    private final List<Edge> edges;
    private Set<Node> settledNodes;
    private Set<Node> unSettledNodes;
    private Map<Node, Node> predecessors;
    private Map<Node, Integer> distance;
    
    public Dijkstra(MultiGraph graph) {
        // create a copy of the array so that we can operate on this array
        this.nodes = new ArrayList<>(graph.getNodeSet());
        this.edges = new ArrayList<>(graph.getEdgeSet());
    }
    
    public void execute(Node source) {
        settledNodes = new HashSet<>();
        unSettledNodes = new HashSet<>();
        distance = new HashMap<>();
        predecessors = new HashMap<>();
        distance.put(source, 0);
        unSettledNodes.add(source);
        while (unSettledNodes.size() > 0) {
            Node node = getMinimum(unSettledNodes);
            settledNodes.add(node);
            unSettledNodes.remove(node);
            findMinimalDistances(node);
        }
    }
        
    private void findMinimalDistances(Node node) {
        List<Node> adjacentNodes = getNeighbors(node);
        for (Node target : adjacentNodes) {
            if (getShortestDistance(target) > getShortestDistance(node)
                + getDistance(node, target)) {
                distance.put(target, getShortestDistance(node)
                    + getDistance(node, target));
                predecessors.put(target, node);
                unSettledNodes.add(target);
            }
        }

    }
    
    private int getDistance(Node node, Node target) {
        for (Edge edge : edges) {
          if (edge.getSourceNode().equals(node)
              && edge.getTargetNode().equals(target)) {
            return edge.getAttribute("time");
          }
        }
        throw new RuntimeException("Should not happen");
    }
  
    private List<Node> getNeighbors(Node node) {
        List<Node> neighbors = new ArrayList<>();
        for (Edge edge : edges) {
          if (edge.getSourceNode().equals(node)
              && !isSettled(edge.getTargetNode())) {
            neighbors.add(edge.getTargetNode());
          }
        }
        return neighbors;
    }

  private Node getMinimum(Set<Node> vertexes) {
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

  private boolean isSettled(Node vertex) {
    return settledNodes.contains(vertex);
  }

  private int getShortestDistance(Node destination) {
    Integer d = distance.get(destination);
    if (d == null) {
      return Integer.MAX_VALUE;
    } else {
      return d;
    }
  }

  /*
   * This method returns the path from the source to the selected target and
   * NULL if no path exists   */
  public Path getPath(Node target) {
    Path path = new Path();
    Node step = target;
    // check if a path exists
    if (predecessors.get(step) == null) {
      return null;
    }
    path.add(step, step.getEdgeBetween(predecessors.get(step)));
    while (predecessors.get(step) != null) {
      step = predecessors.get(step);
      path.add(step, step.getEdgeBetween(predecessors.get(step)));
    }
    // Put it into the correct order
    //Collections.reverse(path);
 
    return path;
  }


    
}
