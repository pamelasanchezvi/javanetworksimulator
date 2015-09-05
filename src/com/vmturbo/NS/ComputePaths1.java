/**
 * @author shangshangchen
 */
package com.vmturbo.NS;

import java.util.ArrayList;
import java.util.HashMap;



public class ComputePaths1 {

    //matrix is a HashMap of HashMaps
    //matrix stores a list of paths between all pairs of hosts
    private HashMap<Host, HashMap<Host, ArrayList<Path>>> matrix = new HashMap<>();
    private Graph graph = new Graph();
    private DFS dfs = new DFS(graph);

    private ArrayList<SpineSwitch> spines;
    private ArrayList<ToRSwitch> tors;
    private ArrayList<Host> hosts;
    private ArrayList<Link> links;



    public ComputePaths1(ArrayList<SpineSwitch> spines, ArrayList<ToRSwitch> tors,
                    ArrayList<Host> hosts, ArrayList<Link> links) {

        this.spines = spines;
        this.tors = tors;
        this.hosts = hosts;
        this.links = links;

        for (SpineSwitch spine : this.spines) {
            for (ToRSwitch tor : spine.getToRList()) {
                graph.addTwoWayEdge(spine, tor);
            }
        }
    }


    public void run() {

        //create an empty |hosts| × |hosts| matrix
        for (Host source : hosts) {
            HashMap<Host, ArrayList<Path>> destinations = new HashMap<>();
            for (Host dest : hosts) {
                destinations.put(dest, new ArrayList<Path>());
            }
            matrix.put(source, destinations);
        }

        //fill the matrix
        for (Host source : hosts) {
            for (Host dest : hosts) {
                findPaths(source, dest);
            }
        }
    }

    /** 
     * 
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
                    ArrayList<Link> l1s = Utility.getMultiLinks(h1, tor1, this.links);
                    ArrayList<Link> l2s = Utility.getMultiLinks(tor1, h2, this.links);
                    for (Link l1 : l1s) {
                        for (Link l2 : l2s) {
                            ArrayList<Link> pathLinks = new ArrayList<>();
                            pathLinks.add(l1);
                            pathLinks.add(l2);
                            Path newPath = new Path(h1, h2, pathLinks);
                            getPaths(h1, h2).add(newPath);
                        }
                    }


                }
                else {

                    for (ArrayList<Node> seq_nodes : dfs.run(tor1, tor2)) {
                        seq_nodes.add(0, h1);
                        seq_nodes.add(h2);
                        SuperPath sp = new SuperPath(seq_nodes);
                        for (ArrayList<Link> pathLinks : sp.multiply()) {
                            Path newPath = new Path(h1, h2, pathLinks);
                            getPaths(h1, h2).add(newPath);
                        }
                    }

                }
            }
        }
    }

    public ArrayList<Path> getPaths(Host source, Host dest) {
        return matrix.get(source).get(dest);
    }
}
