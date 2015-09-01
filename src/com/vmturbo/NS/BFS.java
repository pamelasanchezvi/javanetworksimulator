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


    /**
     * BFS shouldn't be run on hosts, since a host can't be an intermediate node in a path 
     * @param nodes: only switches, NO hosts
     */
    public BFS(ArrayList<Node> nodes) {
        this.nodes = nodes;
        color = new HashMap<>();
        distance = new HashMap<>();
        predecessor = new HashMap<>();
        matrix = new HashMap<>();
    }

    public void run() {
        for (Node source : nodes) {
            runBFS(source);
            HashMap<Node, Integer> row = new HashMap<>();
            for (Node dest : nodes) {
                row.put(dest, distance.get(dest));
            }
            matrix.put(source, row);
        }
    }

    private void runBFS(Node s) {
        for (Node node : nodes) {
            color.put(node, WHITE);
            distance.put(node, Integer.MAX_VALUE);
            predecessor.put(node, null);
        }
        color.put(s, GRAY);
        distance.put(s, 0);
        Queue<Node> queue = new LinkedList<>();
        queue.add(s);
        while (!queue.isEmpty()) {
            Node u = queue.remove();
            for (Node v : getNeighbors(u)) {
                if (color.get(v) == WHITE) {
                    color.put(v, GRAY);
                    distance.put(v, distance.get(u) + 1);
                    predecessor.put(v, u);
                    queue.add(v);
                }
            }
            color.put(u, BLACK);
        }
    }

    private ArrayList<Node> getNeighbors(Node node) {
        ArrayList<Node> neighbors = new ArrayList<>();

        /**
        if (node instanceof Host) {
            neighbors.addAll(((Host)node).getToRSwitch());
        }
        */
        if (node instanceof ToRSwitch) {
            //neighbors.addAll(((ToRSwitch)node).getHostList());
            neighbors.addAll(((ToRSwitch)node).getSpineList());
        }
        if (node instanceof SpineSwitch) {
            neighbors.addAll(((SpineSwitch)node).getToRList());
        }
        return neighbors;
    }

    public int getDistance(Node n1, Node n2) {
        return matrix.get(n1).get(n2);
    }

    public void print() {
        System.out.println("  " + matrix.keySet());
        for (Node source : matrix.keySet()) {
            String s = source.getName() + ": ";
            for (Node dest : matrix.get(source).keySet()) {
                int distance = matrix.get(source).get(dest);
                if (distance < Integer.MAX_VALUE) {
                    s += distance + "  ";
                }
                else {
                    s += "-" + "  ";
                }

            }
            System.out.println(s);
        }
    }

}
