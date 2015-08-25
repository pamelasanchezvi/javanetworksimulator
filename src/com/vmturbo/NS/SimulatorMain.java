/**
 *
 */
package com.vmturbo.NS;

import java.util.ArrayList;

/**
 * @author kunal, shangshang, pamela
 *
 */
public class SimulatorMain {

    ArrayList<SpineSwitch> spineList;
    ArrayList<ToRSwitch> torList;
    ArrayList<Host> hostList;
    ArrayList<Link> linkList;
    ArrayList<Flow> flowQueue;
    private double avgLinkUtil;
    private double maxLinkUtil;
    private double stdevLinkUtil;
    
    private static String TOPOFILE = "input/topology.txt";
    private static String QUEUEFILE = "input/flowQueue.txt";
/*
    public void calculateLinkUtil(ArrayList<Link> linkList){
    	double sumUtilHostTor;
    	double sumUtilTorSpine;
    	for(Link link: linkList){
    		if(link.getSrcNode().)
    	}
    }*/
    
    public double getAvgLinkUtil(){
    	return avgLinkUtil;
    }
    
    public double getMaxLinkUtil(){
    	return maxLinkUtil;
    }
    
    public double getStdevLinkUtil(){
    	return stdevLinkUtil;
    }
    
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        SimulatorMain simulator = new SimulatorMain();

        // discover the topology
        TopologySetup topo = TopologySetup.getInstance();
        topo.setTopologyFileName(TOPOFILE);
        topo.parseFile();

        // populate the FlowQueue
        FlowQueueSetup queue = new FlowQueueSetup(QUEUEFILE);
        queue.populateQueue();
        simulator.flowQueue = queue.getFlowQueue();

        // compute all the paths
        ComputePaths comPaths = new ComputePaths(topo.spineList, topo.torList, topo.hostList,
                                                 topo.linkList);
        comPaths.findPaths();
        ArrayList<Path> allPaths;

        // Initial link utilization
      /*  simulator.calculateLinkUtil(topo.linkList);
        System.out.println("Link Utilization initially:");
        System.out.println("Avg: " + simulator.getAvgLinkUtil() 
        		       	+ "\tMax: " + simulator.getMaxLinkUtil()
        		       	+ "\tStdev: " + simulator.getStdevLinkUtil());
        */// find random placement
        // find random placement
        for (Flow flow : simulator.flowQueue) {
            allPaths = comPaths.getPaths(flow.source, flow.dest);
            if (allPaths.isEmpty()) {
                System.err.println("No path found for flow: source: " + flow.source
                                   + "\tdest:"
                                   + flow.dest);
            }
            
            RandomPlacement.randomPlacement(flow, allPaths);
            /*
            simulator.calculateLinkUtil(topo.linkList);
            System.out.println("Link Utilization after placing Flow: "
            					+ flow.getSource().getName()
            					+ " -> "
            					+ flow.getDest().getName()
            					+ " Flow bandwidth: " 
            					+ flow.getBandwidth());
            System.out.println("Avg: " + simulator.getAvgLinkUtil() 
            		       	+ "\tMax: " + simulator.getMaxLinkUtil()
            		       	+ "\tStdev: " + simulator.getStdevLinkUtil());
            */
        }
    }

}
