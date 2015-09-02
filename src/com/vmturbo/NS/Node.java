/**
 * 
 */
package com.vmturbo.NS;

import java.util.ArrayList;

/**
 * @author kunal
 * @author shangshangchen: added toString() and link-related methods
 *
 */
public abstract class Node {

    protected String name;
    //should really be a Set, but since we are doing everything in ArrayList...
    protected ArrayList<Link> outgoingLinks = new ArrayList<>();

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    public void setName(String nodeName) {
        this.name = nodeName;
    }

    //=================@author: shangshang=========
    //node has a list of outgoing links
    public void addOutgoingLink(Link link) {
        if (!outgoingLinks.contains(link) &&
            link.getSrcNode() != null &&
            link.getSrcNode().equals(this)) {
            outgoingLinks.add(link);
        }

    }

    public void removeOutgoingLink(Link link) {
        outgoingLinks.remove(link);
    }

    public ArrayList<Link> getOutgoingLinks() {
        return outgoingLinks;
    }

    //for testing
    public static void main(String[] args) {
        Host a = new Host("a");
        Host b = new Host("b");
        Link l1 = new Link(a, b, 10, 0, null);
        Link l2 = new Link(a, b, 10, 0, null);
        a.addOutgoingLink(l1);
        a.addOutgoingLink(l2);
        a.addOutgoingLink(l1);
        System.out.println(a.getOutgoingLinks());

    }
}
