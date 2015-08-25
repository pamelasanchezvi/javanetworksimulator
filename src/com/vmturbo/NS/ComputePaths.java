/**
 * @author shangshangchen 
 * We assume static topology for now, i.e. no method for updating paths when a link is down.
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
     * Constructor: just takes in params. For actual computation, use findPaths()
     * All nodes are passed in as "ArrayLists", maybe we will switch to "Set" later
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
     * Main method for computing paths for all pairs
     * This method only works for leaf-spine architecture.
     * For general graphs, we need something like BFS.
     * For now, it only considers obvious paths, 
     * e.g. host->tor->host, or host->tor->spine->tor->host.
     * Later, we will consider paths that bounce off multiple spines.
     * @param spines
     * @param tors
     * @param hosts
     * @param links
     */
    public void findPaths() {

        //create an empty |hosts| × |hosts| matrix
        for (Host source : hosts) {
            HashMap<Host, ArrayList<Path>> destinations = new HashMap<>();
            for (Host dest : hosts) {
                destinations.put(dest, null);
            }
            matrix.put(source, destinations);
        }

        System.out.println(matrix);

        //populate the matrix
        for (Host source : hosts) {
            for (Host dest : hosts) {
                findPaths(source, dest);
            }
        }

    }

    /**
     * Helper method for findPaths()
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

        //set up topology
        Host a = new Host("a");
        Host b = new Host("b");
        Host c = new Host("c");
        ArrayList<Host> hosts = new ArrayList<Host>();
        hosts.add(a);
        hosts.add(b);
        hosts.add(c);
        ComputePaths computer = new ComputePaths(null, null, hosts, null);
        computer.findPaths();

        ToRSwitch tor1 = new ToRSwitch("tor1");
        ToRSwitch tor2 = new ToRSwitch("tor2");
        tor1.addHost(a);
        tor1.addHost(b);
        tor2.addHost(c);
        SpineSwitch spine = new SpineSwitch("spine");
        spine.addtorSwitch(tor1);
        spine.addtorSwitch(tor2);
        Link l1 = new Link(a, tor1, 1, 0.5);
        Link l2 = new Link(tor1, b, 1, 0.5);
        Link l3 = new Link(tor1, spine, 10, 0);
        Link l4 = new Link(spine, tor2, 10, 0);
        Link l5 = new Link(tor2, c, 1, 0.5);
    }
    //*/
}
