/**
 * 
 */
package com.vmturbo.NS;

/**
 * @author kunal
 *
 */
public abstract class Node {

	protected String name;
	
	public String getName(){
		return name;
	}
	
	public void setName(String nodeName){
		this.name = nodeName;
	}
}
