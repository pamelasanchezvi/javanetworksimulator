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
		double bandwidth;
		
		try{
			reader = new BufferedReader(new FileReader(queueFileName)); 
			// read line by line
			while((readLine = reader.readLine()) != null){
				
				readLine.trim();
				flowData = readLine.split(";");

				if(flowData.length == 5){
					// construct Flow object
					start = Integer.parseInt(flowData[0].trim());
					duration = Integer.parseInt(flowData[1].trim());
					source = TopologySetup.getInstance().getHost(flowData[2].trim());		// method should be implemented in TopologySetup
					destination = TopologySetup.getInstance().getHost(flowData[3].trim());
					bandwidth = Double.parseDouble(flowData[4].trim());
					Flow flowToAdd = new Flow(source, destination, start, duration, bandwidth);
					//System.out.println(source);
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
	
}
