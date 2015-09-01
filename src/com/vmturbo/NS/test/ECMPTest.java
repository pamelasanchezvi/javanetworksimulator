/**
 * @author shangshangchen
 */
package com.vmturbo.NS.test;

import java.util.ArrayList;

import com.vmturbo.NS.ECMPPlacement;
import com.vmturbo.NS.Flow;
import com.vmturbo.NS.Host;
import com.vmturbo.NS.Link;
import com.vmturbo.NS.SpineSwitch;
import com.vmturbo.NS.ToRSwitch;
import com.vmturbo.NS.Utility;



public class ECMPTest {

    public static void main(String[] args) {

        //create 3 types of nodes
        Host a = new Host("a");
        Host b = new Host("b");
        Host c = new Host("c");

        ToRSwitch tor1 = new ToRSwitch("1");
        ToRSwitch tor2 = new ToRSwitch("2");
        ToRSwitch tor3 = new ToRSwitch("3");

        SpineSwitch spineA = new SpineSwitch("A");
        SpineSwitch spineB = new SpineSwitch("B");
        SpineSwitch spineC = new SpineSwitch("C");


        //set "neighbors" for each node
        Utility.connectNodes(a, tor1);
        Utility.connectNodes(b, tor2);
        Utility.connectNodes(c, tor3);
        Utility.connectNodes(tor1, spineA);
        Utility.connectNodes(tor2, spineA);
        Utility.connectNodes(tor3, spineA);
        Utility.connectNodes(tor1, spineB);
        Utility.connectNodes(tor2, spineB);
        Utility.connectNodes(tor3, spineB);
        Utility.connectNodes(tor1, spineC);
        Utility.connectNodes(tor2, spineC);
        Utility.connectNodes(tor3, spineC);

        //create 4 lists to pass to ECMP
        ArrayList<Host> hosts = new ArrayList<>();
        ArrayList<ToRSwitch> tors = new ArrayList<>();
        ArrayList<SpineSwitch> spines = new ArrayList<>();
        ArrayList<Link> links = new ArrayList<>();
        hosts.add(a);
        hosts.add(b);
        hosts.add(c);
        tors.add(tor1);
        tors.add(tor2);
        tors.add(tor3);
        spines.add(spineA);
        spines.add(spineB);
        spines.add(spineC);

        //create duplex links, and add them to the "links" list
        Utility.addDuplexLink(a, tor1, "0/1", "0/1", 0, links);
        Utility.addDuplexLink(b, tor2, "0/1", "0/1", 0, links);
        Utility.addDuplexLink(c, tor3, "0/1", "0/1", 0, links);
        Utility.addDuplexLink(spineA, tor1, "0/10", "0/10", 0, links);
        Utility.addDuplexLink(spineA, tor1, "0/10", "0/10", 1, links);
        Utility.addDuplexLink(spineA, tor2, "0/10", "0/10", 0, links);
        Utility.addDuplexLink(spineA, tor3, "0/10", "0/10", 0, links);
        Utility.addDuplexLink(spineB, tor1, "0/10", "0/10", 0, links);
        Utility.addDuplexLink(spineB, tor2, "0/10", "0/10", 0, links);
        Utility.addDuplexLink(spineB, tor2, "0/10", "0/10", 1, links);
        Utility.addDuplexLink(spineB, tor3, "0/10", "0/10", 0, links);
        Utility.addDuplexLink(spineC, tor1, "0/10", "0/10", 0, links);
        Utility.addDuplexLink(spineC, tor2, "0/10", "0/10", 0, links);
        Utility.addDuplexLink(spineC, tor3, "0/10", "0/10", 0, links);
        Utility.addDuplexLink(spineC, tor3, "0/10", "0/10", 1, links);

        //run ECMP
        ECMPPlacement ecmp = new ECMPPlacement(spines, tors, hosts, links);
        ecmp.print();
        Flow flow = new Flow(a, b, 0, 10, 0.5);
        System.out.println(ecmp.recommendPath(flow));
        //System.out.println(links);

    }

}
