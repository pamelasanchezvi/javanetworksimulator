/**
 * @author shangshangchen
 * http://stackoverflow.com/questions/58306/graph-algorithm-to-find-all-connections-between-two-arbitrary-vertices
 */
package com.vmturbo.NS;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;


public class Graph {
    private Map<Node, LinkedHashSet<Node>> map = new HashMap<>();

    public void addEdge(Node n1, Node n2) {
        LinkedHashSet<Node> adjacent = map.get(n1);
        if (adjacent == null) {
            adjacent = new LinkedHashSet<>();
            map.put(n1, adjacent);
        }
        adjacent.add(n2);
    }

    public void addTwoWayEdge(Node n1, Node n2) {
        addEdge(n1, n2);
        addEdge(n2, n1);
    }

    public LinkedList<Node> adjacentNodes(Node node) {
        LinkedHashSet<Node> adjacent = map.get(node);
        if (adjacent == null) {
            return new LinkedList<>();
        }
        return new LinkedList<>(adjacent);
    }


}
