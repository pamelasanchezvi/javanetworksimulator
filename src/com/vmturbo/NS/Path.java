package com.vmturbo.NS;

import java.util.ArrayList;

public class Path {

    Host source;
    ArrayList<Switch> switches;
    Host dest;

    public Path(Host source, Host dest, ArrayList<Switch> switches) {
        this.switches = switches;
        this.source = source;
        this.dest = dest;
    }

    public Host getSource() {
        return source;
    }

    public Host getDest() {
        return dest;
    }



}
