/**
 * 
 */
package com.vmturbo.NS;

import java.util.ArrayList;

/**
 * @author kunal
 *
 */
public class Host {

	String name;
	ArrayList<ToRSwitch> torList; 
	
	public Host(String name, ArrayList<ToRSwitch> torList){
		this.name = name;
		this.torList = torList;
	}
	
	public String getName(){
		return name;
	}
	
	public ArrayList<ToRSwitch> getToR(){
		return torList;
	}
	
	public void setName(String hostname){
		this.name = hostname;
	}
	
	public void addtorSwitch(ToRSwitch torSwitch){
		if(torList.contains(torSwitch)){
			return;
		}
		torList.add(torSwitch);
	}
	
	public void removetorSwitch(ToRSwitch torSwitch){
		if(torList.contains(torSwitch)){
			torList.remove(torSwitch);
		}
	}
	
}
