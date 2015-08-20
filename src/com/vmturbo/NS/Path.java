package com.vmturbo.NS;

import java.util.ArrayList;

public class Path {

    Host source;
    ArrayList<Switch> nodes;
    Host destination;

    public Path(ArrayList<String> nodes) {
        this.nodes = nodes;
    }

    public String getSource() {
        return this.nodes.get(0);
    }

    public String getDest() {
        return this.nodes.get(nodes.size() - 1);
    }


    public static void main(String[] args) {
        ArrayList<String> path0 = new ArrayList
        Path path1 = new Path(nodes);
    }
}
