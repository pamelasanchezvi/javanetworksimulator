/**
 * 
 */
package com.vmturbo.NS;

/**
 * @author kunal
 *
 */
public class Host {

	String name;
	ToRSwitch tor; 
	
	public Host(String name, ToRSwitch tor){
		this.name = name;
		this.tor = tor;
	}
	
	public String getName(){
		return name;
	}
	
	public ToRSwitch getToR(){
		return tor;
	}
	
	public void setName(String hostname){
		this.name = hostname;
	}
	
	public void setToRSwitch(ToRSwitch torSwitch){
		this.tor = torSwitch;
	}
	
}
