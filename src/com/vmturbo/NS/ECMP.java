/**
 * @author shangshangchen
 */
package com.vmturbo.NS;

import java.util.ArrayList;
import java.util.Random;


public class ECMP {

    /**
     * ECMP supposedly uses a hash function to randomly choose a next-hop among \ 
     * multiple equally best next-hops. But we don't know about hashing functions \
     * can achieve uniform randomness, so we just use random number generator here.
     * 
     * @param flow: the flow that needs placement
     * @param allPaths: a list of all paths in the network, regardless of usage
     * @return: a randomly chosen path  
     */
    public static Path ECMPPlacement(Flow flow, ArrayList<Path> allPaths) {

        //find only paths that match the source-destination of the flow
        ArrayList<Path> paths = new ArrayList<Path>();
        for (Path path : allPaths) {
            if (path.getSource().equals(flow.getSource()) &&
                path.getDest().equals(flow.getDest())) {
                paths.add(path);
            }
        }

        if (paths.isEmpty()) {
            System.out.println("no matching path exists for " + flow.getAllInfo());
            return null;
        }
        else { // choose a random path
            Random rand = new Random();
            int n = rand.nextInt(paths.size());
            Path path = paths.get(n);
            if (path.sell(flow) == 0) { //if selling is successful
                return path;
            }
            else {
                System.out.println("selling failed between: \n" +
                                   flow.getAllInfo() +
                                   path.getAllInfo());
                return null;
            }
        }
    }

}
