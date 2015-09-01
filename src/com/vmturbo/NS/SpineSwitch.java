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

    public SpineSwitch(String name){
        this.name = name;
        this.torList = new ArrayList<ToRSwitch>();
    }

    public ArrayList<ToRSwitch> getToRList(){
        return torList;
    }

    public void addtorSwitch(ToRSwitch torSwitch){
        if(torSwitch == null){
            System.out.println("torSwitch is null");
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

    public boolean exists(String otherswitch) {
        if (otherswitch!=null && this.name.equals(otherswitch)){
            return true;
        }
        return false;
    }
}
