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
	ArrayList<FlowEvent> flowQueue;
	private double avgLinkUtilHosttoToR;
	private double maxLinkUtilHosttoToR;
	private double stdevLinkUtilHosttoToR;
	private double avgLinkUtilToRtoSpine;
	private double maxLinkUtilToRtoSpine;
	private double stdevLinkUtilToRtoSpine;

	private static String TOPOFILE = "input/symmetric-topology";
	private static String QUEUEFILE = "input/flow-sameTime";

	/**
	 * Calculate average, max and std dev of link utilization
	 * @param linkList
	 */
	public void calculateLinkUtil(ArrayList<Link> linkList){
		double sumUtilHostTor = 0;
		double sumUtilTorSpine = 0;
		double maxHostTor = 0;
		double maxTorSpine = 0;
		double tempUtil = 0;
		double tempStdevHostTor = 0;
		double tempStdevTorSpine = 0;

		// calculate max and average link utilization
		for(Link link: linkList){
			switch(link.getLinkType()){
			case HOSTTOTOR:
				tempUtil = link.getUtilization();
				sumUtilHostTor += tempUtil;
				if(maxHostTor < tempUtil){
					maxHostTor = tempUtil;
				}
				break;
			case TORTOSPINE:
				tempUtil = link.getUtilization();
				sumUtilTorSpine += tempUtil;
				if(maxTorSpine < tempUtil){
					maxTorSpine = tempUtil;
				}
				break;
			}
		}
		
		avgLinkUtilHosttoToR = sumUtilHostTor/linkList.size();
		avgLinkUtilToRtoSpine = sumUtilTorSpine/linkList.size();
		maxLinkUtilHosttoToR = maxHostTor;
		maxLinkUtilToRtoSpine = maxTorSpine;
		
		// calculate variance
		for(Link link: linkList){
			switch(link.getLinkType()){
			case HOSTTOTOR:
				tempUtil = link.getUtilization();
				tempStdevHostTor += Math.pow(tempUtil - avgLinkUtilHosttoToR, 2);
				break;
			case TORTOSPINE:
				tempUtil = link.getUtilization();
				tempStdevTorSpine += Math.pow(tempUtil - avgLinkUtilToRtoSpine, 2);
				break;
			}
		}
		
		// calculate std dev
		stdevLinkUtilHosttoToR = Math.sqrt(tempStdevHostTor/linkList.size());
		stdevLinkUtilToRtoSpine = Math.sqrt(tempStdevTorSpine/linkList.size());
	}

	public double getAvgLinkUtilHostTor(){
		return avgLinkUtilHosttoToR;
	}

	public double getMaxLinkUtilHostTor(){
		return maxLinkUtilHosttoToR;
	}

	public double getStdevLinkUtilHostTor(){
		return stdevLinkUtilHosttoToR;
	}

	public double getAvgLinkUtilTorSpine(){
		return avgLinkUtilToRtoSpine;
	}

	public double getMaxLinkUtilTorSpine(){
		return maxLinkUtilToRtoSpine;
	}

	public double getStdevLinkUtilTorSpine(){
		return stdevLinkUtilToRtoSpine;
	}

	public void printMetrics(ArrayList<Link> linkList){
		for(Link link : linkList){
			System.out.println("Link: " + link.getSrcNode().getName() + " -> " + link.getDestNode().getName() + " ,util: " + link.getUtilization());
		}
		System.out.println("Avg H to ToR: " + getAvgLinkUtilHostTor() 
				+ "\tMax H to ToR: " + getMaxLinkUtilHostTor()
				+ "\tStdev H to ToR: " + getStdevLinkUtilHostTor());
		System.out.println("Avg ToR to Spine: " + getAvgLinkUtilTorSpine() 
				+ "\tMax ToR to Spine: " + getMaxLinkUtilTorSpine()
				+ "\tStdev ToR to Spine: " + getStdevLinkUtilTorSpine());
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
		System.out.println("Size of flow event queue: " + simulator.flowQueue.size());

		// compute all the paths
		ComputePaths comPaths = new ComputePaths(topo.spineList, topo.torList, topo.hostList,
				topo.linkList);
		comPaths.findPaths();
		ArrayList<Path> allPaths;

		// Initial link utilization
		simulator.calculateLinkUtil(topo.linkList);
		System.out.println("Link Utilization initially:");
		simulator.printMetrics(topo.linkList);

		// find random placement
		// find random placement
		for (FlowEvent flowEvent : simulator.flowQueue) {
			Flow flow = flowEvent.getFlow();
			switch(flowEvent.getFlowEventType()){
			case END:
				flow.getPath().removeFlow(flow);
				System.err.println("Flow event end " + flow.getSource()
						+ "\tdest:"
						+ flow.getDest()
						+ "\t Start: "
						+ flow.getStart() + "\t Bandwidth: "
						+ flow.getBandwidth());
				simulator.calculateLinkUtil(topo.linkList);
				simulator.printMetrics(topo.linkList);   
				break;
			case START:
				allPaths = comPaths.getPaths(flow.getSource(), flow.getDest());
				if (allPaths.isEmpty()) {
					System.err.println("No path found for flow: source: " + flow.getSource()
							+ "\tdest:"
							+ flow.getDest()
							+ "\t Start: "
							+ flow.getStart() + "\t Bandwidth: "
							+ flow.getBandwidth());
				}

				Path pathSelected = RandomPlacement.randomPlacement(flow, allPaths);
				//Path pathSelected = EconomicPlacement.econPlacement(flow, allPaths);
				pathSelected.placeFlow(flow);
				
				
				simulator.calculateLinkUtil(topo.linkList);
				System.err.println("Link Utilization after placing Flow: "
						+ flow.getSource().getName()
						+ " -> "
						+ flow.getDest().getName()
						+ " Flow bandwidth: " 
						+ flow.getBandwidth());
				simulator.printMetrics(topo.linkList);   
				break;
			default:
				break;
			}			  
			 
		}
	}

}
