/**
 * 
 */
package com.vmturbo.NS;

/**
 * @author kunal
 *
 */
public class FlowEvent implements Comparable<FlowEvent> {

	public enum FlowEventType{
		START, END
	}
	private int eventTime;
	private FlowEventType feType;
	private Flow flow;
	
	public FlowEvent(int eventTime, FlowEventType feType, Flow flow){
		this.eventTime = eventTime;
		this.feType = feType;
		this.flow = flow;
	}
	
	public double getEventTime(){
		return eventTime;
	}
	
	public FlowEventType getFlowEventType(){
		return feType;
	}
	
	public Flow getFlow(){
		return flow;
	}
	
	public void setEventTime(int eventTime){
		this.eventTime = eventTime;
	}
	
	public void setFlowEventType(FlowEventType feType){
		this.feType = feType;
	}
	
	public void setFlow(Flow flow){
		this.flow = flow;
	}

	@Override
	public int compareTo(FlowEvent o) {
		if(this.eventTime > o.eventTime){
			return 1;
		} else if(this.eventTime < o.eventTime){
			return -1;
		} else if(this.eventTime == o.eventTime){
			if(this.feType == FlowEventType.START && o.feType == FlowEventType.START){
				return 1;
			} else if(this.feType == FlowEventType.START && o.feType == FlowEventType.END){
				return 1;
			} else if(this.feType == FlowEventType.END && o.feType == FlowEventType.START){
				return -1;
			} else {
				return 0;
			}
		}
		return 0;
	}
	
	
}
