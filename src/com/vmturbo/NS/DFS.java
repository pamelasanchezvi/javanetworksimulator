/**
 * @author shangshangchen
 * http://stackoverflow.com/questions/58306/graph-algorithm-to-find-all-connections-between-two-arbitrary-vertices
 */
package com.vmturbo.NS;

import java.util.ArrayList;
import java.util.LinkedList;

public class DFS {


    private Graph graph;
    private Node start;
    private Node end;
    private ArrayList<ArrayList<Node>> superPaths;

    public DFS(Graph graph) {
        this.graph = graph;
    }

    public ArrayList<ArrayList<Node>> run(Node start, Node end) {
        this.start = start;
        this.end = end;
        superPaths = new ArrayList<>();

        LinkedList<Node> visited = new LinkedList<>();
        visited.add(this.start);
        runDFS(visited);

        return superPaths;

    }

    private void runDFS(LinkedList<Node> visited) {
        LinkedList<Node> nodes = graph.adjacentNodes(visited.getLast());

        for (Node node : nodes) {
            if (visited.contains(node)) {
                continue;
            }
            if (node.equals(end)) {
                visited.add(node);
                //printPath(visited);
                superPaths.add(new ArrayList<Node>(visited));
                visited.removeLast();
                break;
            }
        }

        for (Node node : nodes) {
            if (visited.contains(node) || node.equals(end)) {
                continue;
            }
            visited.addLast(node);
            runDFS(visited);
            visited.removeLast();
        }

    }

    private void printPath(LinkedList<Node> visited) {
        for (Node node : visited) {
            System.out.print(node);
            System.out.print(" ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        // this graph is directional
        Graph graph = new Graph();
        /**
        Node A = new Host("A");
        Node B = new Host("B");
        Node C = new Host("C");
        Node D = new Host("D");
        Node E = new Host("E");
        Node F = new Host("F");

        graph.addEdge(A, B);
        graph.addEdge(A, C);
        graph.addEdge(B, A);
        graph.addEdge(B, D);
        graph.addEdge(B, E); // this is the only one-way connection
        graph.addEdge(B, F);
        graph.addEdge(C, A);
        graph.addEdge(C, E);
        graph.addEdge(C, F);
        graph.addEdge(D, B);
        graph.addEdge(E, C);
        graph.addEdge(E, F);
        graph.addEdge(F, B);
        graph.addEdge(F, C);
        graph.addEdge(F, E);
        */

        Node tor1 = new ToRSwitch("1");
        Node tor2 = new ToRSwitch("2");
        Node tor3 = new ToRSwitch("3");

        Node spineA = new SpineSwitch("A");
        Node spineB = new SpineSwitch("B");
        Node spineC = new SpineSwitch("C");


        graph.addTwoWayEdge(spineA, tor1);
        graph.addTwoWayEdge(spineA, tor2);
        graph.addTwoWayEdge(spineA, tor3);
        graph.addTwoWayEdge(spineB, tor1);
        graph.addTwoWayEdge(spineB, tor2);
        graph.addTwoWayEdge(spineB, tor3);
        graph.addTwoWayEdge(spineC, tor1);
        graph.addTwoWayEdge(spineC, tor2);
        graph.addTwoWayEdge(spineC, tor3);


        new DFS(graph).run(tor1, tor3);
    }

}
