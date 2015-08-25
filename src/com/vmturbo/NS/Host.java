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

    public Host(String name){
        this.name = name;
        this.torList = new ArrayList<ToRSwitch>();
    }

    public ArrayList<ToRSwitch> getToRSwitch(){
        return torList;
    }

    public void addtorSwitch(ToRSwitch torSwitch){
        if(torSwitch == null){
            System.err.println("Adding null ToRSwitch");
            return;
        }
        if(torList.contains(torSwitch)){
            System.out.println("Host " + name + " already contains the ToRSwitch " + torSwitch.name);
            return;
        }
        torList.add(torSwitch);
    }

    public void removetorSwitch(ToRSwitch torSwitch){
        if(torSwitch == null){
            System.err.println("Removing null ToRSwitch");
            return;
        }
        if(!torList.contains(torSwitch)){
            System.out.println("Host " + name + " does not contain the ToRSwitch " + torSwitch.name);
            return;
        }
        torList.remove(torSwitch);
    }
    /*
	public static void main(String[] args){

        Host a = new Host("a");
        Host b = new Host("b");
        ToRSwitch tor1 = new ToRSwitch("tor1");
        ToRSwitch tor2 = new ToRSwitch("tor2");
        a.addtorSwitch(tor1);
        a.addtorSwitch(tor2);
        a.addtorSwitch(tor2);
        b.addtorSwitch(tor1);
        System.out.println("Adding tor1 and tor2 to host a");
        System.out.println("Host " + a.name + " should be 'a'" );
        System.out.println("Host a is connected to " + a.torList.size() + " tors, should be 2");
        System.out.println("Host " + b.name + " should be 'b'" );
        System.out.println("Host b is connected to " + b.torList.size() + " tors, should be 1");
        System.out.println("Removing tor2 from host a");
        a.removetorSwitch(tor2);
        System.out.println("Host a is connected to " + a.torList.size() + " tors, should be 1");
        System.out.println("Removing tor1 from host a");
        a.removetorSwitch(tor1);
        System.out.println("Host a is connected to " + a.torList.size() + " tors, should be 0");
        a.removetorSwitch(tor1);
        System.out.println("Host a is connected to " + a.torList.size() + " tors, should be 0");

	}
     */
    public boolean exists(String otherswitch) {
        if (otherswitch!=null && this.name.equals(otherswitch)){
            return true;
        }
        return false;
    }
}
