/**
 * @author shangshangchen testing push
 */

package com.vmturbo.NS;

import java.util.ArrayList;
import java.util.HashMap;



public class ComputePaths {

    //matrix is a HashMap of HashMaps
    //matrix stores a list of paths between all pairs of hosts
    private HashMap<Host, HashMap<Host, ArrayList<Path>>> matrix;
    private ArrayList<SpineSwitch> spines;
    private ArrayList<ToRSwitch> tors;
    private ArrayList<Host> hosts;
    private ArrayList<Link> links;

    /**
     * we assume static topology for now
     * all nodes are passed in as ArrayLists, maybe will switch to "Set" later
     * @param spines
     * @param tors
     * @param hosts
     * @param links
     */
    public ComputePaths(ArrayList<SpineSwitch> spines, ArrayList<ToRSwitch> tors,
                    ArrayList<Host> hosts, ArrayList<Link> links) {
        matrix = new HashMap<Host, HashMap<Host, ArrayList<Path>>>();
        this.spines = spines;
        this.tors = tors;
        this.hosts = hosts;
        this.links = links;
    }

    /**
     * This method only works for leaf-spine architecture.
     * For general graphs, we need something like BFS.
     * @param spines
     * @param tors
     * @param hosts
     * @param links
     */
    public void findPaths() {

        //create an empty |hosts| × |hosts| matrix
        for (Host host : hosts) {
            HashMap<Host, ArrayList<Path>> destinations = new HashMap<>();
            destinations.put(host, null);
            matrix.put(host, destinations);
        }

        //populate the matrix
        for (Host source : hosts) {
            for (Host dest : hosts) {
                findPaths(source, dest);
            }
        }

    }

    /**
     * Again, it only works for spine-leaf architecture
     * @param h1: source host
     * @param h2: destination host
     */
    private void findPaths(Host h1, Host h2) {
        if (h1.equals(h2))
            return;

        ArrayList<ToRSwitch> tors1 = h1.getToRSwitch();
        ArrayList<ToRSwitch> tors2 = h2.getToRSwitch();
        if (tors1 == null || tors2 == null)
            return;

        for (ToRSwitch tor1 : tors1) {
            for (ToRSwitch tor2 : tors2) {
                if (tor1.equals(tor2)) {
                    //add path: h1->tor1->h2 to the cell (h1,h2)
                    ArrayList<Link> pathLinks = new ArrayList<Link>();
                    Link l1 = findLink(h1, tor1);
                    Link l2 = findLink(tor1, h2);
                    if (l1 != null && l2 != null &&
                        pathLinks.add(l1) && pathLinks.add(l2)) {
                        Path newPath = new Path(h1, h2, pathLinks);
                        getPaths(h1, h2).add(newPath);
                    }

                }
                else { // find common spines connected to both tor1 and tor2
                    ArrayList<SpineSwitch> spines1 = tor1.getSpineList();
                    ArrayList<SpineSwitch> spines2 = tor2.getSpineList();
                    if (spines1 == null || spines2 == null)
                        continue;
                    for (SpineSwitch spine1 : spines1) {
                        if (spines2.contains(spine1)) {
                            //add path: h1->tor1->spine1->tor2->h2
                            ArrayList<Link> pathLinks = new ArrayList<Link>();
                            Link l1 = findLink(h1, tor1);
                            Link l2 = findLink(tor1, spine1);
                            Link l3 = findLink(spine1, tor2);
                            Link l4 = findLink(tor2, h2);
                            if (l1 != null && pathLinks.add(l1) &&
                                l2 != null && pathLinks.add(l2) &&
                                l3 != null && pathLinks.add(l3) &&
                                l4 != null && pathLinks.add(l4)) {
                                Path newPath = new Path(h1, h2, pathLinks);
                                getPaths(h1, h2).add(newPath);
                            }
                        }
                    }
                }
            }
        }
    }


    /**
     * helper function: 
     * find the link from node1 to node2 by looping through all links
     * @param n1: source node
     * @param n2: destination node
     * @return
     */
    private Link findLink(Node n1, Node n2) {
        for (Link link : links) {
            if (link.getSrcNode().equals(n1) &&
                link.getDestNode().equals(n2)) {
                return link;
            }
        }
        return null;
    }


    public ArrayList<Path> getPaths(Host source, Host dest) {
        return matrix.get(source).get(dest);
    }

    ///**for testing 
    public static void main(String[] args) {

    }
    //*/
}
