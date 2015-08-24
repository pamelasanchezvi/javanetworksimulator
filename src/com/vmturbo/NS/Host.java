/**
 * 
 */
package com.vmturbo.NS;

import java.util.ArrayList;

/**
 * @author kunal
 *
 */
public class Host extends Node {

	private ArrayList<ToRSwitch> torList; 
	
	public Host(String name, ArrayList<ToRSwitch> torList){
		this.name = name;
		this.torList = torList;
	}
	
	public ArrayList<ToRSwitch> getToRSwitch(){
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
	/*
	public static void main(String[] args){
		Host host = new Host("testhost", null);
		ToRSwitch torsw = null;
		host.addtorSwitch(torsw);
		
	}
	*/
}
