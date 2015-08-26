/**
 * 
 */
package com.vmturbo.NS;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import com.vmturbo.NS.FlowEvent.FlowEventType;

/**
 * @author kunal
 *
 */
public class FlowQueueSetup {

	private String queueFileName;
	private ArrayList<FlowEvent> flowQueue;
	
	public FlowQueueSetup(String fileName){
		this.queueFileName = fileName;
		flowQueue = new ArrayList<FlowEvent>();
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
		int startTime;
		int duration;
		int endTime;
		double bandwidth;
		
		try{
			reader = new BufferedReader(new FileReader(queueFileName)); 
			// read line by line
			while((readLine = reader.readLine()) != null){
				
				readLine.trim();
				flowData = readLine.split(";");

				if(flowData.length == 5){
					// construct Flow object
					startTime = Integer.parseInt(flowData[0].trim());
					duration = Integer.parseInt(flowData[1].trim());
					source = TopologySetup.getInstance().getHost(flowData[2].trim());		// method should be implemented in TopologySetup
					destination = TopologySetup.getInstance().getHost(flowData[3].trim());
					bandwidth = Double.parseDouble(flowData[4].trim());
					Flow flowToAdd = new Flow(source, destination, startTime, duration, bandwidth);
					//System.out.println(source);
					// add FlowEvent object to queue
					endTime = startTime + duration;
					FlowEvent startEvent = new FlowEvent(startTime, FlowEventType.START, flowToAdd);
					FlowEvent endEvent = new FlowEvent(endTime, FlowEventType.END, flowToAdd);
					addFlowEvent(startEvent);
					addFlowEvent(endEvent);
				} else {
					reader.close();
					throw new IllegalArgumentException();
				}
			}
			reader.close();
			sortFlowQueue();
		} catch (IOException ioe){
			System.err.println("Caught IOException: " + ioe.getMessage());
		}
	}
	
	/**
	 * Sorts the flow queue based on the event trigger time in an ascending order
	 */
	private void sortFlowQueue() {
		Collections.sort(flowQueue);
	}

	public String getQueueFileName(){
		return queueFileName;
	}
	
	public ArrayList<FlowEvent> getFlowQueue(){
		return flowQueue;
	}
	
	public void addFlowEvent(FlowEvent event){
		if(event == null){
			return;
		}
		if(flowQueue.contains(event)){
			return;
		}
		flowQueue.add(event);
	}
	
	public void removeFlowEvent(FlowEvent event){
		if(event == null){
			return;
		}
		if(flowQueue.contains(event)){
			flowQueue.remove(event);
		}
	}
	
	public void setQueueFileName(String fileName){
		this.queueFileName = fileName;
	}
	
}
