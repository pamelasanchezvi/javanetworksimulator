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
			System.err.println("Adding null host to TOR " + this.name);
			return;
		}
		if(hostList.contains(host)){
			System.out.println("HostList for TOR " + this.name + " already contains the host " + host.name);
			return;
		}
		hostList.add(host);
	}
	
	public void addSpine(SpineSwitch spSwitch){
		if(spSwitch == null){
			System.err.println("Adding null spine switch to TOR " + this.name);
			return;
		}
		if(spineList.contains(spSwitch)){
			System.out.println("SpineList for TOR " + this.name + " already contains the spine " + spSwitch.name);
			return;
		}
		spineList.add(spSwitch);
	}
	
	public void removeHost(Host host){
		if(host == null){
			System.err.println("Removing null host from TOR " + this.name);
			return;
		}
		if(!hostList.contains(host)){
			System.out.println("HostList for TOR " + this.name + " does not contain the host " + host.name);
			return;
		}
		hostList.remove(host);
	}
	
	public void removeSpine(SpineSwitch spSwitch){
		if(spSwitch == null){
			System.err.println("Removing null spine switch from TOR " + this.name);
			return;
		}
		if(!spineList.contains(spSwitch)){
			System.out.println("SpineList for TOR " + this.name + " does not contain the spine " + spSwitch.name);
			return;
		}
		spineList.remove(spSwitch);
	}
	
	public static void main(String[] args){
		Host a = new Host("a");
        Host b = new Host("b");
        Host c = new Host("c");
        ToRSwitch tor1 = new ToRSwitch("tor1");
        ToRSwitch tor2 = new ToRSwitch("tor2");
        tor1.addHost(a);
        a.addtorSwitch(tor1);
        tor1.addHost(b);
        tor2.addHost(c);
        SpineSwitch spine = new SpineSwitch("spine");
        spine.addtorSwitch(tor1);
        spine.addtorSwitch(tor2);
        tor1.addSpine(spine);
        Link l1 = new Link(a, tor1, 1, 0.5);
        Link l2 = new Link(tor1, b, 1, 0.5);
        Link l3 = new Link(tor1, spine, 10, 2);
        Link l4 = new Link(spine, tor2, 10, 2);
        Link l5 = new Link(tor2, c, 1, 0.5);



        ArrayList<Link> links1 = new ArrayList<Link>();
        links1.add(l1);
        links1.add(l2);

        ArrayList<Link> links2 = new ArrayList<Link>();
        links2.add(l1);
        links2.add(l3);
        links2.add(l4);
        links2.add(l5);

	}
}
