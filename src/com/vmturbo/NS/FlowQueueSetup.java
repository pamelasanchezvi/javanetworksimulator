/**
 * 
 */
package com.vmturbo.NS;

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
