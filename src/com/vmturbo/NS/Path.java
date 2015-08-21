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

    public String getAllInfo() {
        return ("path: " + source.getName() + " -> " + dest.getName() + "\n");
    }

    /**
     * assuming we are not doing multi-threading, no need for locks
     * @param flow
     * @return -1 if path can't satisfy flow demand, an integer quote otherwise
     */
    public int getQuote(Flow flow) {
        //"demand" is the bandwidth demand of the flow
        double demand = (double)flow.getSize() / (double)flow.getDuration();

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
        double demand = (double)flow.getSize() / (double)flow.getDuration();
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
        double demand = (double)flow.getSize() / (double)flow.getDuration();
        for (Link link : links) {
            link.setUtilization(Math.max(0, link.getUtilization() - demand));
        }
        return 0;
    }

    /** testing
    public static void main(String[] args) {
        
    }
    */

}
