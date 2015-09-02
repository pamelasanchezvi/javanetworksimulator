package com.vmturbo.NS;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author shangshangchen
 * this class is to make topology construction easier, i.e. fewer codes,
 * but you can always use individual constructors for each node and link.
 */
public class Utility {

    /**
     * 
     * @param n1: source node
     * @param n2: destination node
     * @return an ArrayList of all parallel links from n1 to n2, empty ArrayList if no such link exists
     */
    public static ArrayList<Link> getMultiLinks(Node n1, Node n2, Collection<Link> links) {
        ArrayList<Link> multiLinks = new ArrayList<>();
        for (Link link : links) {
            if (link.getSrcNode().equals(n1) &&
                link.getDestNode().equals(n2)) {
                multiLinks.add(link);
            }
        }
        return multiLinks;
    }


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
                                       ArrayList<Link> links) {
        Link[] duplex = new Link[2];
        if (n1 == null || n2 == null) {
            System.out.println("Utility.addDuplexLink: null pointer passed");
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

        int num = 0;
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
