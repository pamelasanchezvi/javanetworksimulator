/**
 * @author shangshangchen
 */
package com.vmturbo.NS;

import java.util.ArrayList;

public class EconomicPlacement {

    /**
     * All placement algorithms only give recommendations without actual act of placement
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
            System.out.println("no matching path exists for " + flow.toString());
            return null;
        }

        //find cheapest path
        double minQuote = Double.MAX_VALUE;
        int pathNo = 0;
        for (int i = 0; i < paths.size(); i++) {
            double quote = paths.get(i).getQuote(flow);
            //System.out.println(quote);
            if (quote >= 0 && quote < minQuote) {//quote can be -1 if path can't accommodate flow
                minQuote = quote;
                pathNo = i;
            }
        }
        /**if at the end, minQuote is still Double.Max_Value, 
         * it means that no path can accommodate the flow, 
         * but we assume this won't happen. 
         * Given that flow has infinite budget now, it will always be able to buy cheapest path
         */


        //check if flow has enough budget for the cheapest path
        if (flow.getBudget() >= minQuote) {
            return paths.get(pathNo);

        }
        else {
            System.out.println("not enough budget: \n" +
                               flow.toString() + "\n" +
                               "minimum Quote is " + minQuote);
            return null;
        }

    }

}
