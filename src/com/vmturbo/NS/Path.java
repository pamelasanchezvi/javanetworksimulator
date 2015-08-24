/**
 * @author shangshangchen
 */
package com.vmturbo.NS;

import java.util.ArrayList;

public class Path {

    Host source;
    ArrayList<Link> links;
    Host dest;

    //constructor
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
     * used for economic placement
     * assuming we are not doing multi-threading, no need for locks
     * @param flow
     * @return -1 if path can't satisfy flow demand, an integer quote otherwise
     */
    public int getQuote(Flow flow) {
        //"demand" is the bandwidth demand of the flow
        double demand = flow.getBandwidth();

        //quote of the path is the sum of link prices
        //link price is calculated as 1/(1-U)^2, U is percentage utilized of the link
        int quote = 0;
        for (Link link : links) {
            double bandwidthLeft = link.getCapacity() - link.getUtilization();
            if (demand >= bandwidthLeft) {
                return -1;
            }
            double percentage = (demand + link.getUtilization()) / link.getCapacity();
            double linkPrice = 1 / Math.pow(1 - percentage, 2);
            quote += linkPrice;
        }
        return quote;
    }


    /**
     * used for economic placement
     * @param flow
     * @return 0 if successful, -1 unsuccessful
     */
    public int placeFlow(Flow flow) {
        double demand = flow.getBandwidth();
        for (Link link : links) {
            //for now, just push everything into the link
            link.setUtilization(Math.min(link.getCapacity(), link.getUtilization() + demand));
        }
        return 0;
    }

    /**
     * used for economic placement
     * @param flow
     * @return 0 if successful, -1 unsuccessful
     */
    public int removeFlow(Flow flow) {
        double demand = flow.getBandwidth();
        for (Link link : links) {
            link.setUtilization(Math.max(0, link.getUtilization() - demand));
        }
        return 0;
    }

    ///** testing
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


        Path p1 = new Path(a, b, links1);
        Path p2 = new Path(a, c, links2);
        ArrayList<Path> paths = new ArrayList<Path>();
        paths.add(p1);
        paths.add(p2);
        //test toString() 
        System.out.println(paths);

        //test getQuote()
        Flow f1 = new Flow(a, b, 0, 10, 0.1); //should print 12
        System.out.println(p1.getQuote(f1));
        Flow f2 = new Flow(a, b, 0, 10, 0.4); //should print 200
        System.out.println(p1.getQuote(f2));
        Flow f3 = new Flow(a, b, 0, 10, 0.5); //should print -1
        System.out.println(p1.getQuote(f3));
        Flow f4 = new Flow(a, c, 0, 10, 0.1);
        System.out.println(p2.getQuote(f4));

    }
    //*/

}
