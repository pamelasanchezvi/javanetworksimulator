/**
 * @author shangshangchen
 */
package com.vmturbo.NS;

import java.util.ArrayList;
import java.util.Random;


public class RandomPlacement {

    /**
     * 
     * @param flow: the flow that needs placement
     * @param allPaths: a list of all paths in the network, regardless of usage
     * @return: a randomly chosen path  
     */
    public static Path randomPlacement(Flow flow, ArrayList<Path> allPaths) {

        //find only paths that match the source-destination of the flow
        ArrayList<Path> paths = new ArrayList<Path>();
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
            Path path = paths.get(n);
            //not sure if the actual placement should be carried out here
            //or should placement algorithm only give recommendations?
            if (path.placeFlow(flow) == 0) { //if selling is successful
                return path;
            }
            else {
                System.out.println("selling failed between: \n" +
                                   flow.toString() + "\n" +
                                   path.toString());
                return null;
            }
        }
    }

    //testing is carried out in ComputePaths.java

}
