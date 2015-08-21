/**
 * 
 */
package com.vmturbo.NS;

import java.util.ArrayList;

/**
 * @author kunal
 *
 */
public class ToRSwitch {

	String name;
	ArrayList<Host> hostList;
	ArrayList<SpineSwitch> spineList;
	
	public ToRSwitch(String name, ArrayList<Host> hostList, ArrayList<SpineSwitch> spineList){
		this.name = name;
		this.hostList = hostList;
		this.spineList = spineList;
	}
	
	public String getName(){
		return name;
	}
	
	public ArrayList<Host> getHostList(){
		return hostList;
	}
	
	public ArrayList<SpineSwitch> getSpineList(){
		return spineList;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public void addHost(Host host){
		if(hostList.contains(host)){
			return;
		}
		hostList.add(host);
	}
	
	public void addSpine(SpineSwitch spSwitch){
		if(spineList.contains(spSwitch)){
			return;
		}
		spineList.add(spSwitch);
	}
	
	public void removeHost(Host host){
		if(hostList.contains(host)){
			hostList.remove(host);
		}
	}
	
	public void removeSpine(SpineSwitch spSwitch){
		if(spineList.contains(spSwitch)){
			spineList.remove(spSwitch);
		}
	}
}
