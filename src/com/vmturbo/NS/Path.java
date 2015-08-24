/**
 * @author shangshangchen
 */
package com.vmturbo.NS;

import java.util.ArrayList;

public class Path {

    Host source;
    ArrayList<Link> links;
    Host dest;

    public Path(Host source, Host dest, ArrayList<Link> links) {
        this.links = links;
        this.source = source;
        this.dest = dest;
    }

    public Host getSource() {
        return source;
    }

    public Host getDest() {
        return dest;
    }

    @Override
    public String toString() {
        if (links == null) {
            return ("path: " + source.getName() + " -> " + dest.getName());
        }
        else {
            String s = "path: " + source.getName();
            for (Link link : links) {
                s += " -> " + link.getDestNode().getName();
            }
            return s;
        }
    }



    /**
     * assuming we are not doing multi-threading, no need for locks
     * @param flow
     * @return -1 if path can't satisfy flow demand, an integer quote otherwise
     */
    public int getQuote(Flow flow) {
        //"demand" is the bandwidth demand of the flow
        double demand = (double)flow.getBandwidth() / (double)flow.getDuration();

        //quote of the path is the sum of link prices
        //link price is calculated as 1/(1-U)^2, U is percentage utilized of the link
        int quote = 0;
        for (Link link : links) {
            double bandwidthLeft = link.getCapacity() - link.getUtilization();
            if (demand >= bandwidthLeft) {
                return -1;
            }
            double percentage = (demand + link.getUtilization()) / link.getCapacity();
            quote += 1 / Math.pow(1 - percentage, 2);
        }
        return quote;
    }

    /**
     * @param flow
     * @return 0 if successful, -1 unsuccessful
     */
    public int placeFlow(Flow flow) {
        double demand = (double)flow.getBandwidth() / (double)flow.getDuration();
        for (Link link : links) {
            //for now, just push everything into the link
            link.setUtilization(Math.min(link.getCapacity(), link.getUtilization() + demand));
        }
        return 0;
    }

    /**
     * @param flow
     * @return 0 if successful, -1 unsuccessful
     */
    public int removeFlow(Flow flow) {
        double demand = (double)flow.getBandwidth() / (double)flow.getDuration();
        for (Link link : links) {
            link.setUtilization(Math.max(0, link.getUtilization() - demand));
        }
        return 0;
    }

    /** testing
    public static void main(String[] args) {

        Host a = new Host("a");
        Host b = new Host("b");
        Host c = new Host("c");
        ToRSwitch tor1 = new ToRSwitch("tor1");
        ToRSwitch tor2 = new ToRSwitch("tor2");
        tor1.addHost(a);
        tor1.addHost(b);
        tor2.addHost(c);
        SpineSwitch spine = new SpineSwitch("spine");
        spine.addtorSwitch(tor1);
        spine.addtorSwitch(tor2);
        Link l1 = new Link(a, tor1, 0, 0);
        Link l2 = new Link(tor1, b, 0, 0);
        Link l3 = new Link(tor1, spine, 0, 0);
        Link l4 = new Link(spine, tor2, 0, 0);
        Link l5 = new Link(tor2, c, 0, 0);



        ArrayList<Link> links1 = new ArrayList<Link>();
        links1.add(l1);
        links1.add(l2);

        ArrayList<Link> links2 = new ArrayList<Link>();
        links2.add(l1);
        links2.add(l3);
        links2.add(l4);
        links2.add(l5);


        Path p1 = new Path(a, b, links1);
        Path p2 = new Path(a, c, links2);
        ArrayList<Path> paths = new ArrayList<Path>();
        paths.add(p1);
        paths.add(p2);
        System.out.println(paths);
    }
    */

}
