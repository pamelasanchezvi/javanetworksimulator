/**
 * 
 */
package com.vmturbo.NS;

import java.util.ArrayList;

/**
 * @author kunal
 *
 */
public class SpineSwitch {

	String name;
	ArrayList<ToRSwitch> torList;
	
	public SpineSwitch(String name, ArrayList<ToRSwitch> torList){
		this.name = name;
		this.torList = torList;
	}
	
	public String getName(){
		return name;
	}
	
	public ArrayList<ToRSwitch> getToRList(){
		return torList;
	}
	
	public void setName(String name){
		this.name = name;
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
