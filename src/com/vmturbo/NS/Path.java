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
     * needs implementation and locks
     * @param flow
     * @return 
     */
    public int getQuote(Flow flow) {
        return 0;
    }

    /**
     * needs implementation and locks
     * @param flow
     * @return 0 if successful, -1 unsuccessful
     */
    public int sell(Flow flow) {
        return 0;
    }

}
