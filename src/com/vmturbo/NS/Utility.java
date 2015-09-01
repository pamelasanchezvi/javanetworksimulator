/**
 * @author shangshangchen
 */
package com.vmturbo.NS;

import java.util.ArrayList;

public class Utility {



    public static int connectNodes(Node n1, Node n2) {
        if ((n1 instanceof Host) && (n2 instanceof ToRSwitch)) {
            Host host = (Host)n1;
            ToRSwitch tor = (ToRSwitch)n2;
            host.addtorSwitch(tor);
            tor.addHost(host);
            return 0;
        }
        if ((n2 instanceof Host) && (n1 instanceof ToRSwitch)) {
            Host host = (Host)n2;
            ToRSwitch tor = (ToRSwitch)n1;
            host.addtorSwitch(tor);
            tor.addHost(host);
            return 0;
        }
        if ((n1 instanceof ToRSwitch) && (n2 instanceof SpineSwitch)) {
            ToRSwitch tor = (ToRSwitch)n1;
            SpineSwitch spine = (SpineSwitch)n2;
            tor.addSpine(spine);
            spine.addtorSwitch(tor);
            return 0;
        }
        if ((n2 instanceof ToRSwitch) && (n1 instanceof SpineSwitch)) {
            ToRSwitch tor = (ToRSwitch)n2;
            SpineSwitch spine = (SpineSwitch)n1;
            tor.addSpine(spine);
            spine.addtorSwitch(tor);
            return 0;
        }
        System.out.println("Utility.connectNodes: can't connect " + n1 + " and " + n2);
        return -1;
    }

    public static Link[] addDuplexLink(Node n1, Node n2, String s1, String s2,
                                       int num, ArrayList<Link> links) {
        Link[] duplex = new Link[2];
        if (n1 == null || n2 == null) {
            System.out.println("Utility.addLink: null pointer passed");
            return null;
        }
        int utilization, capacity;

        utilization = Integer.parseInt(s1.split("/")[0]);
        capacity = Integer.parseInt(s1.split("/")[1]);
        Link link = new Link(n1, n2, capacity, utilization, null);
        duplex[0] = link;
        links.add(link);

        utilization = Integer.parseInt(s2.split("/")[0]);
        capacity = Integer.parseInt(s2.split("/")[1]);
        Link rlink = new Link(n2, n1, capacity, utilization, null);
        duplex[1] = rlink;
        links.add(rlink);


        if (num == 0) {
            link.setName(n1.getName() + "-" + n2.getName());
            rlink.setName(n2.getName() + "-" + n1.getName());
        }
        else {
            link.setName(n1.getName() + "-" + n2.getName() + "." + num);
            rlink.setName(n2.getName() + "-" + n1.getName() + "." + num);
        }

        return duplex;

    }
}
