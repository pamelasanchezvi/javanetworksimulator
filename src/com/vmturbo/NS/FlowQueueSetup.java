/**
 * 
 */
package com.vmturbo.NS;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author kunal
 *
 */
public class FlowQueueSetup {

	private String queueFileName;
	private ArrayList<Flow> flowQueue;
	
	public FlowQueueSetup(String fileName){
		this.queueFileName = fileName;
		flowQueue = new ArrayList<Flow>();
	}
	
	/**
	 * Format for each line: start time, duration, flow source host, flow destination host, flow bandwidth (Gb/s)
	 */
	public void populateQueue(){
		
		BufferedReader reader;
		String readLine;
		String[] flowData;
		Host source;
		Host destination;
		int start;
		int duration;
		int bandwidth;
		
		try{
			reader = new BufferedReader(new FileReader(queueFileName)); 
			// read line by line
			while((readLine = reader.readLine()) != null){
				System.out.println(readLine);
				readLine.trim();
				flowData = readLine.split(";");

				if(flowData.length == 5){
					// construct Flow object
					start = Integer.parseInt(flowData[0]);
					duration = Integer.parseInt(flowData[1]);
					source = TopologySetup.getInstance().getHost(flowData[2]);		// method should be implemented in TopologySetup
					destination = TopologySetup.getInstance().getHost(flowData[3]);
					bandwidth = Integer.parseInt(flowData[4]);
					Flow flowToAdd = new Flow(source, destination, start, duration, bandwidth);

					// add Flow object to queue
					addFlow(flowToAdd);
				} else {
					reader.close();
					throw new IllegalArgumentException();
				}
			}
			reader.close();
		} catch (IOException ioe){
			System.err.println("Caught IOException: " + ioe.getMessage());
		}
	}
	
	public String getQueueFileName(){
		return queueFileName;
	}
	
	public ArrayList<Flow> getFlowQueue(){
		return flowQueue;
	}
	
	public void addFlow(Flow flow){
		if(flow == null){
			return;
		}
		if(flowQueue.contains(flow)){
			return;
		}
		flowQueue.add(flow);
	}
	
	public void removeFlow(Flow flow){
		if(flow == null){
			return;
		}
		if(flowQueue.contains(flow)){
			flowQueue.remove(flow);
		}
	}
	
	public void setQueueFileName(String fileName){
		this.queueFileName = fileName;
	}
	
	public static void main(String[] args){
		// construct the topology
		Host a = new Host("a");
        Host b = new Host("b");
        Host c = new Host("c");
        ToRSwitch tor1 = new ToRSwitch("tor1");
        ToRSwitch tor2 = new ToRSwitch("tor2");
        tor1.addHost(a);
        tor1.addHost(b);
        tor2.addHost(c);
        SpineSwitch spine = new SpineSwitch("spine");
        spine.addtorSwitch(tor1);
        spine.addtorSwitch(tor2);
        Link l1 = new Link(a, tor1, 1, 0.5);
        Link l2 = new Link(tor1, b, 1, 0.5);
        Link l3 = new Link(tor1, spine, 10, 2);
        Link l4 = new Link(spine, tor2, 10, 2);
        Link l5 = new Link(tor2, c, 1, 0.5);



        ArrayList<Link> links1 = new ArrayList<Link>();
        links1.add(l1);
        links1.add(l2);

        ArrayList<Link> links2 = new ArrayList<Link>();
        links2.add(l1);
        links2.add(l3);
        links2.add(l4);
        links2.add(l5);
	}
}
