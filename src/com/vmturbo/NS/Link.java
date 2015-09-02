/**
 *
 */
package com.vmturbo.NS;

/**
 * @author kunal
 *
 */
public class Link {

    private Node srcNode;
    private Node destNode;
    private double capacity;
    private double utilization;
    private LinkType type;
    private String name = "";

    public enum LinkType {
        HOSTTOTOR, TORTOSPINE
    }

    public Link(Node srcNode, Node destNode, double capacity, double utilization, LinkType type) {
        this.srcNode = srcNode;
        this.destNode = destNode;
        this.capacity = capacity;
        this.utilization = utilization;
        this.type = type;
    }

    //another constructor
    public Link(Node srcNode, Node destNode, double capacity, LinkType type) {
        this.srcNode = srcNode;
        this.destNode = destNode;
        this.capacity = capacity;
        this.utilization = 0;
        this.type = type;
    }

    public Node getSrcNode() {
        return srcNode;
    }

    public Node getDestNode() {
        return destNode;
    }

    public double getCapacity() {
        return capacity;
    }

    public double getUtilization() {
        return utilization;
    }

    public LinkType getLinkType() {
        return type;
    }

    public void setSrcNode(Node src) {
        if (src == null) {
            return;
        }
        this.srcNode = src;

    }

    public void setDestNode(Node dest) {
        if (dest == null) {
            return;
        }
        this.destNode = dest;
    }

    public void setCapacity(double capac) {
        if (capac < 0) {
            return;
        }
        this.capacity = capac;
    }

    public void setUtilization(double util) {
        if (util < 0) {
            return;
        }
        this.utilization = util;
    }

    //optional name of the link,
    //so that we can distinguish parallel links between the same two nodes
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        if (this.name.trim().equals("")) {
            return srcNode + " -> " + destNode;
        }
        else {
            return this.name;
        }

    }

    public boolean exists(String src, String dest) {
        if (src != null && this.srcNode.name.equals(src) && this.destNode.name.equals(dest)) {
            return true;
        }
        return false;
    }


}
