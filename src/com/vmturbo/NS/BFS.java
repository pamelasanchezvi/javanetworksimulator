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

    public void print() {
        System.out.println(matrix.keySet());
        for (Node source : matrix.keySet()) {
            String s = "";
            for (Node dest : matrix.get(source).keySet()) {
                s += matrix.get(source).get(dest) + "  ";
            }
            System.out.println(s);
        }
    }

    public static void main(String[] args) {
        Host a = new Host("a");
        Host b = new Host("b");
        Host c = new Host("c");

        ToRSwitch tor1 = new ToRSwitch("tor1");
        ToRSwitch tor2 = new ToRSwitch("tor2");
        tor1.addHost(a);
        tor1.addHost(b);
        tor2.addHost(b);
        tor2.addHost(c);
        a.addtorSwitch(tor1);
        b.addtorSwitch(tor1);
        b.addtorSwitch(tor2);
        c.addtorSwitch(tor2);

        SpineSwitch spine1 = new SpineSwitch("spine1");
        SpineSwitch spine2 = new SpineSwitch("spine2");
        spine1.addtorSwitch(tor1);
        spine1.addtorSwitch(tor2);
        spine2.addtorSwitch(tor1);
        spine2.addtorSwitch(tor2);
        tor1.addSpine(spine1);
        tor1.addSpine(spine2);
        tor2.addSpine(spine1);
        tor2.addSpine(spine2);


        Link l1 = new Link(a, tor1, 1, 0, null);
        Link l1r = new Link(tor1, a, 1, 0, null);

        Link l2 = new Link(b, tor1, 1, 0, null);
        Link l2r = new Link(tor1, b, 1, 0, null);

        Link l3 = new Link(tor1, spine1, 10, 1.2, null);
        Link l3r = new Link(spine1, tor1, 10, 0, null);

        //Link l3a = new Link(tor1, spine1, 10, 0, null);
        //Link l3ar = new Link(spine1, tor1, 10, 0, null);

        Link l4 = new Link(tor2, spine1, 10, 0, null);
        Link l4r = new Link(spine1, tor2, 10, 0, null);

        //Link l4a = new Link(tor2, spine1, 10, 0, null);
        //Link l4ar = new Link(spine1, tor2, 10, 0, null);

        //Link l4b = new Link(tor2, spine1, 10, 0, null);
        //Link l4br = new Link(spine1, tor2, 10, 0, null);

        Link l5 = new Link(c, tor2, 1, 0, null);
        Link l5r = new Link(tor2, c, 1, 0, null);

        Link l6 = new Link(b, tor2, 1, 0, null);
        Link l6r = new Link(tor2, b, 1, 0, null);

        Link l7 = new Link(tor1, spine2, 10, 1.25, null);
        Link l7r = new Link(spine2, tor1, 10, 0, null);

        Link l8 = new Link(tor2, spine2, 10, 0, null);
        Link l8r = new Link(spine2, tor2, 10, 0, null);


        ArrayList<Host> hosts = new ArrayList<>();
        ArrayList<Link> links = new ArrayList<>();
        ArrayList<ToRSwitch> tors = new ArrayList<>();
        ArrayList<SpineSwitch> spines = new ArrayList<>();
        hosts.add(a);
        hosts.add(b);
        hosts.add(c);
        tors.add(tor1);
        tors.add(tor2);
        spines.add(spine1);
        spines.add(spine2);
        links.add(l1);
        links.add(l1r);
        links.add(l2);
        links.add(l2r);
        links.add(l3);
        links.add(l3r);
        links.add(l4);
        links.add(l4r);
        links.add(l5);
        links.add(l5r);
        links.add(l6);
        links.add(l6r);
        links.add(l7);
        links.add(l7r);
        links.add(l8);
        links.add(l8r);

        ArrayList<Node> allNodes = new ArrayList<>();
        allNodes.addAll(hosts);
        allNodes.addAll(tors);
        allNodes.addAll(spines);

        BFS bfs = new BFS(allNodes);
        bfs.findPaths();
        bfs.print();
    }
}
