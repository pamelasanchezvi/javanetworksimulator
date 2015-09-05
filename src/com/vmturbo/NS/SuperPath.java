/**
 * @author shangshangchen
 */
package com.vmturbo.NS;

import java.util.ArrayList;
import java.util.Collection;

public class SuperPath {


    private ArrayList<Node> nodes;


    public SuperPath(Collection<Node> nodes) {
        this.nodes = new ArrayList<>(nodes);
    }

    public ArrayList<ArrayList<Link>> multiply() {
        if (nodes.isEmpty() || nodes == null)
            return new ArrayList<>();

        ArrayList<ArrayList<Link>> prePaths = new ArrayList<>();
        prePaths.add(new ArrayList<Link>());
        for (int i = 0; i < nodes.size() - 1; i++) {
            Node current = nodes.get(i);
            Node next = nodes.get(i + 1);
            ArrayList<Link> multiLinks = Utility.getMultiLinks(current, next,
                                                               current.getOutgoingLinks());
            if (multiLinks == null || multiLinks.isEmpty())
                return new ArrayList<>();

            ArrayList<ArrayList<Link>> newList = new ArrayList<>();
            for (ArrayList<Link> oldPrePath : prePaths) {
                for (Link link : multiLinks) {
                    ArrayList<Link> newPrePath = new ArrayList<>(oldPrePath);
                    newPrePath.add(link);
                    newList.add(newPrePath);
                }
            }
            prePaths = newList;
        }

        //print(prePaths);

        return prePaths;
    }

    private void print(ArrayList<ArrayList<Link>> prePaths) {
        System.out.println("superPath" + nodes);
        for (ArrayList<Link> prePath : prePaths) {
            System.out.println(prePath);
        }
    }

    public static void main(String[] args) {
        //create 3 types of nodes

        Host a = new Host("a");
        Host b = new Host("b");

        ToRSwitch tor1 = new ToRSwitch("1");
        ToRSwitch tor2 = new ToRSwitch("2");

        SpineSwitch spineA = new SpineSwitch("A");


        ArrayList<Host> hosts = new ArrayList<>();
        ArrayList<ToRSwitch> tors = new ArrayList<>();
        ArrayList<SpineSwitch> spines = new ArrayList<>();
        ArrayList<Link> links = new ArrayList<>();
        hosts.add(a);
        hosts.add(b);
        tors.add(tor1);
        tors.add(tor2);
        spines.add(spineA);



        //connect nodes
        Utility.connectNodes(a, tor1, new String[] {"1|1"}, links);
        Utility.connectNodes(b, tor2, new String[] {"1|1", "1|1"}, links);
        Utility.connectNodes(spineA, tor1, new String[] {"10|10", "10|10", "10|10"}, links);
        Utility.connectNodes(spineA, tor2, new String[] {"10|10"}, links);


        ArrayList<Node> nodes = new ArrayList<>();
        nodes.add(a);
        nodes.add(tor1);
        nodes.add(spineA);
        nodes.add(tor2);
        nodes.add(b);
        SuperPath sp = new SuperPath(nodes);
        sp.multiply();

    }
}
