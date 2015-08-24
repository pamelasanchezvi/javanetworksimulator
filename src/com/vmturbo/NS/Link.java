/**
 * 
 */
package com.vmturbo.NS;

/**
 * @author kunal
 *
 */
public class Link {

	private Node srcNode;
	private Node destNode;
	private double capacity;
	private double utilization;
	
	public Link(Node srcNode, Node destNode, double capacity, double utilization){
		this.srcNode = srcNode;
		this.destNode = destNode;
		this.capacity = capacity;
		this.utilization = utilization;
	}
	
	public Node getSrcNode(){
		return srcNode;
	}
	
	public Node getDestNode(){
		return destNode;
	}
	
	public double getCapacity(){
		return capacity;
	}
	
	public double getUtilization(){
		return utilization;
	}
	
	public void setSrcNode(Node src){
		if(src == null){
			return;
		}
		this.srcNode = src;

	}
	
	public void setDestNode(Node dest){
		if(dest == null){
			return;
		}
		this.destNode = dest;
	}
	
	public void setCapacity(double capac){
		if(capac < 0){
			return;
		}
		this.capacity = capac;
	}
	
	public void setUtilization(double util){
		if(util < 0){
			return;
		}
		this.utilization = util;
	}
	
}
