/**
 * 
 */
package com.vmturbo.NS;

import java.util.ArrayList;

/**
 * @author kunal
 *
 */
public class ToRSwitch extends Node {

	private ArrayList<Host> hostList;
	private ArrayList<SpineSwitch> spineList;
	
	public ToRSwitch(String name){
		this.name = name;
		this.hostList = new ArrayList<Host>();
		this.spineList = new ArrayList<SpineSwitch>();
	}
	
	public ArrayList<Host> getHostList(){
		return hostList;
	}
	
	public ArrayList<SpineSwitch> getSpineList(){
		return spineList;
	}
	
	public void addHost(Host host){
		if(host == null){
			return;
		}
		if(hostList.contains(host)){
			return;
		}
		hostList.add(host);
	}
	
	public void addSpine(SpineSwitch spSwitch){
		if(spSwitch == null){
			return;
		}
		if(spineList.contains(spSwitch)){
			return;
		}
		spineList.add(spSwitch);
	}
	
	public void removeHost(Host host){
		if(host == null){
			return;
		}
		if(hostList.contains(host)){
			hostList.remove(host);
		}
	}
	
	public void removeSpine(SpineSwitch spSwitch){
		if(spSwitch == null){
			return;
		}
		if(spineList.contains(spSwitch)){
			spineList.remove(spSwitch);
		}
	}
}
