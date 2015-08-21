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
     * needs locks?
     * @param flow
     * @return 
     */
    public int getQuote(Flow flow) {
        //"demand" is the bandwidth demand of the flow
        double demand = (double)flow.getSize() / (double)flow.getDuration();

        //quote of the path is the sum of link prices
        //link price is calculated as 1/(1-U)^2
        int quote = 0;
        for (Link link : links) {
            double bandwidthLeft = link.getCapacity() - link.getUtilization();
            if (demand >= bandwidthLeft) {
                return -1;
            }
            double percentage = (demand + link.getUtilization()) /
                                link.getCapacity();
            quote += 1 / Math.pow(1 - percentage, 2);
        }
        return quote;
    }

    /**
     * needs implementation and locks
     * @param flow
     * @return 0 if successful, -1 unsuccessful
     */
    public int sell(Flow flow) {
        return 0;
    }

    public static void main(String[] args) {
        System.out.println(1 / Math.pow());
    }

}
