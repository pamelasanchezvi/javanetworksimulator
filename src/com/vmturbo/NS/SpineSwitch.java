/**
 * 
 */
package com.vmturbo.NS;

import java.util.ArrayList;

/**
 * @author kunal
 *
 */
public class SpineSwitch extends Node {

	private ArrayList<ToRSwitch> torList;
	
	public SpineSwitch(String name, ArrayList<ToRSwitch> torList){
		this.name = name;
		this.torList = torList;
	}
	
	public ArrayList<ToRSwitch> getToRList(){
		return torList;
	}
	
	public void addtorSwitch(ToRSwitch torSwitch){
		if(torSwitch == null){
			return;
		}
		if(torList.contains(torSwitch)){
			return;
		}
		torList.add(torSwitch);
	}
	
	public void removetorSwitch(ToRSwitch torSwitch){
		if(torSwitch == null){
			return;
		}
		if(torList.contains(torSwitch)){
			torList.remove(torSwitch);
		}
	}
}
