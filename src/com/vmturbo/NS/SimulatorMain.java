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
    
	private static String TOPOFILE = "../../../../input/topology.txt";
	private static String QUEUEFILE = "../../../../input/flowQueue.txt";

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
        
        
    }

}
