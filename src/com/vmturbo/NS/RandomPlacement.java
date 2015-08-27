/**
 * @author shangshangchen
 */
package com.vmturbo.NS;

import java.util.ArrayList;
import java.util.Random;


public class RandomPlacement {

    /**
     * All placement algorithms only give recommendations without actual act of placement
     * @param flow: the flow that needs placement
     * @param allPaths: a list of all paths in the network, regardless of usage
     * @return: a randomly chosen path  
     */
    public static Path randomPlacement(Flow flow, ArrayList<Path> allPaths) {

        //find only paths that match the source-destination of the flow
        ArrayList<Path> paths = new ArrayList<>();
        for (Path path : allPaths) {
            if (path.getSource().equals(flow.getSource()) &&
                path.getDest().equals(flow.getDest())) {
                paths.add(path);
            }
        }

        if (paths.isEmpty()) {
            System.out.println("no matching path exists for " + flow.toString());
            return null;
        }
        else { // choose a random path
            Random rand = new Random();
            int n = rand.nextInt(paths.size());
            return paths.get(n);

        }
    }

    //testing is carried out in ComputePaths.java

}
