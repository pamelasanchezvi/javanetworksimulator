/**
 *
 */
package com.vmturbo.NS;

import java.util.ArrayList;

/**
 * @author pamelasanchez
 *
 */
public class MinMaxPlacement {

    /**
     * All placement algorithms only give recommendations without actual act of placement
     * @param flow: the flow that needs placement
     * @param allPaths: a list of all paths in the network
     * @return: a recommended path based on price
     */
    public static void minmaxPlacement(ArrayList<Flow> flows, ArrayList<Path> allPaths) {

        //find only paths that match the source-destination of the flow
        ArrayList<Path> paths = new ArrayList<>();
        ArrayList<Double> caps = new ArrayList<>();
        Double minCap = 10000.00;
        Double totalCap = 0.0;
        int numOfFlows = flows.size();
        int numOfPaths = 0;

        for (Path path : allPaths) {
            if (path.getSource().equals(flows.get(0).getSource()) &&
                path.getDest().equals(flows.get(0).getDest())) {
                paths.add(path);
            }
        }
        numOfPaths = paths.size();

        System.out.println("numOfFlows is " + numOfFlows + " and numOfPaths " +numOfPaths);

        //if no matching path exists
        if (paths.isEmpty()) {
            System.out.println("no matching path exists for " + flows.get(0).toString());

        }

        //find

        for (Path path:paths){
            for(Link lnk:path.getLinks()){
                if (lnk.getCapacity()< minCap){
                    minCap = lnk.getCapacity();
                }
            }
            caps.add(minCap);
        }

        for(Double cap:caps){
            totalCap+= cap;
        }
        if(numOfFlows < totalCap){
            // put numOfFlows/numOfPaths flows on each path
            // use % given that it might not divide equally?
            int  extraflows = numOfFlows%numOfPaths;
            int flowperpath = numOfFlows/numOfPaths;
            int pathCt = 0;

            for(Path path:paths){
                pathCt++;
                for(int i=0;i<flowperpath;i++){
                    path.placeFlow(flows.get(flowperpath*(pathCt-1)+i));
                }
            }
            // here add from extraflows?
            if(extraflows != 0){
                int j = 0;
                for(Path path:paths){
                    path.placeFlow(flows.get(pathCt*flowperpath+j));
                    j++;
                    extraflows--;
                    if(extraflows==0){
                        break;
                    }
                }
            }
        }

    }

}
