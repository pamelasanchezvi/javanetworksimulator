package com.vmturbo.NS;

import java.util.ArrayList;
import java.util.Random;

public class ECMPPlacement {

    /**
     * All placement algorithms only give recommendations without actual act of placement
     * @param flow
     * @param allPaths
     * @return 
     */
    public static Path ecmpPlacement(Flow flow, ArrayList<Path> allPaths) {

        //Filter out paths that don't match source-destination of the flow
        //And find the shortest number of hops
        ArrayList<Path> paths = new ArrayList<Path>();
        int minHops = Integer.MAX_VALUE;
        for (Path path : allPaths) {
            if (path.getSource().equals(flow.getSource()) &&
                path.getDest().equals(flow.getDest())) {
                paths.add(path);
                if (path.getNumHops() < minHops) {
                    minHops = path.getNumHops();
                }
            }
        }

        //if no matching path exists
        if (paths.isEmpty()) {
            System.out.println("no matching path exists for " + flow.toString());
            return null;
        }

        //Choose randomly among the shortest paths
        ArrayList<Path> shortPaths = new ArrayList<Path>();
        for (Path path : paths) {
            if (path.getNumHops() == minHops) {
                shortPaths.add(path);
            }
        }
        Random rand = new Random();
        int n = rand.nextInt(shortPaths.size());
        return shortPaths.get(n);

    }



}
