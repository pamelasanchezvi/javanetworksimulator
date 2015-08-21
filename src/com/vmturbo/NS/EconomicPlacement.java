/**
 * @author shangshangchen
 */
package com.vmturbo.NS;

import java.util.ArrayList;

public class EconomicPlacement {

    /**
     * 
     * @param flow: the flow that needs placement
     * @param allPaths: a list of all paths in the network
     * @return: a recommended path based on price
     */
    public static Path econPlacement(Flow flow, ArrayList<Path> allPaths) {

        //find only paths that match the source-destination of the flow
        ArrayList<Path> paths = new ArrayList<Path>();
        for (Path path : allPaths) {
            if (path.getSource().equals(flow.getSource()) &&
                path.getDest().equals(flow.getDest())) {
                paths.add(path);
            }
        }

        //if no matching path exists
        if (paths.isEmpty()) {
            System.out.println("no matching path exists for " + flow.getAllInfo());
            return null;
        }

        //find cheapest path
        int minQuote = Integer.MAX_VALUE;
        int pathNo = 0;
        for (int i = 0; i < paths.size(); i++) {
            int quote = paths.get(i).getQuote(flow);
            if (quote < minQuote) {
                minQuote = quote;
                pathNo = i;
            }
        }

        //check if flow has enough budget for the cheapest path
        if (flow.getBudget() >= minQuote) {
            Path path = paths.get(pathNo);
            //not sure if the actual placement act should be carried out here
            if (path.placeFlow(flow) == 0) {
                return path;
            }
            else {
                System.out.println("selling failed between: \n" +
                                   flow.getAllInfo() +
                                   path.getAllInfo());
                return null;
            }
        }
        else {
            System.out.println("not enough budget: \n" +
                               flow.getAllInfo() +
                               "minimum Quote is " + minQuote + " \n");
            return null;
        }

    }

}
