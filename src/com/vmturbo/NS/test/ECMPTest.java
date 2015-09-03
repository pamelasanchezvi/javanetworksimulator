/**
 * @author shangshangchen
 */
package com.vmturbo.NS.test;

import java.util.ArrayList;

import com.vmturbo.NS.ECMPPlacement;
import com.vmturbo.NS.Flow;
import com.vmturbo.NS.Host;
import com.vmturbo.NS.Link;
import com.vmturbo.NS.Path;
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

        //connect nodes
        Utility.connectNodes(a, tor1, new String[] {"1|1"}, links);
        Utility.connectNodes(b, tor2, new String[] {"1|1"}, links);
        Utility.connectNodes(c, tor3, new String[] {"1|1"}, links);

        Utility.connectNodes(spineA, tor1, new String[] {"10|10", "10|10"}, links);
        Utility.connectNodes(spineA, tor2, new String[] {"10|10"}, links);
        Utility.connectNodes(spineA, tor3, new String[] {"10|10"}, links);

        Utility.connectNodes(spineB, tor1, new String[] {"10|10"}, links);
        Utility.connectNodes(spineB, tor2, new String[] {"10|10", "10|10"}, links);
        Utility.connectNodes(spineB, tor3, new String[] {"10|10"}, links);

        Utility.connectNodes(spineC, tor1, new String[] {"10|10"}, links);
        Utility.connectNodes(spineC, tor2, new String[] {"10|10"}, links);
        Utility.connectNodes(spineC, tor3, new String[] {"10|10", "10|10"}, links);



        //run ECMP
        ECMPPlacement ecmp = new ECMPPlacement(spines, tors, hosts, links);
        //ecmp.printDistances();        
        for (int i = 0; i < 12; i++) {
            Flow flow = new Flow(a, c, 0, 10, 1);
            Path path = ecmp.recommendPath(flow);
            path.placeFlow(flow);
        }

        Utility.printLinkUsage(links);


    }

}
