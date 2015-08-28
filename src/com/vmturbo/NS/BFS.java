/**
 * @author shangshangchen
 */
package com.vmturbo.NS;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;


public class BFS {

    private final int WHITE = 0;
    private final int GRAY = 1;
    private final int BLACK = 2;

    private ArrayList<Node> nodes;
    private HashMap<Node, HashMap<Node, Integer>> matrix;
    private HashMap<Node, Integer> color;
    private HashMap<Node, Integer> distance;
    private HashMap<Node, Node> predecessor;



    public BFS(ArrayList<Node> nodes) {
        this.nodes = nodes;
        color = new HashMap<>();
        distance = new HashMap<>();
        predecessor = new HashMap<>();
        matrix = new HashMap<>();
    }

    public void findPaths() {

    }

    private void BFS(Node s) {
        for (Node node : nodes) {
            color.put(node, WHITE);
            distance.put(node, Integer.MAX_VALUE);
            predecessor.put(node, null);
        }
        color.put(s, GRAY);
        distance.put(s, 0);
        Queue<Node> queue = new LinkedList<>();
        queue.add(s);
        while (queue.size() > 0) {

        }
    }

    private ArrayList<Node> getNeighbors(Node node) {
        ArrayList<Node> neighbors = new ArrayList<>();
        if (node instanceof Host) {
            neighbors.addAll(((Host)node).getToRSwitch());
        }
        if (node instanceof ToRSwitch) {
            neighbors.addAll(((ToRSwitch)node).getHostList());
            neighbors.addAll(((ToRSwitch)node).getSpineList());
        }
        if (node instanceof SpineSwitch) {
            neighbors.addAll(((SpineSwitch)node).getToRList());
        }
        return neighbors;
    }

}
