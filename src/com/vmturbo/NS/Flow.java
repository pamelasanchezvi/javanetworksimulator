/**
 * @author shangshangchen
 */
package com.vmturbo.NS;

public class Flow {

    Host source, dest;
    int start, duration; //in seconds
    double bandwidth; //bandwidth of the flow
    int budget; // the budge the flow has, in virtual dollars

    //constructor: assuming infinite budget for now
    public Flow(Host source, Host dest, int start, int duration, double bandwidth /*, int budget*/) {
        this.source = source;
        this.dest = dest;
        this.start = start;
        this.duration = duration;
        this.bandwidth = bandwidth;
        this.budget = Integer.MAX_VALUE;
    }

    public Host getSource() {
        return source;
    }

    public Host getDest() {
        return dest;
    }

    public int getStart() {
        return start;
    }

    public int getDuration() {
        return duration;
    }

    //is this method meaningful?
    public int getEnd() {
        return start + duration;
    }

    public double getBandwidth() {
        return bandwidth;
    }

    public int getBudget() {
        return budget;
    }

    @Override
    public String toString() {
        return ("flow: "
                + source.getName() + " -> " + dest.getName() + "; "
                + "starts: " + getStart() + "s; "
                + "duration: " + getDuration() + "s; "
                + bandwidth + " Gb/s; "
                //+ budget + " $;" 
                + "\n");
    }

    /** for testing purpose
    public static void main(String[] args) {
        Host h1 = new Host("h1");
        Host h2 = new Host("h2");
        Flow f = new Flow(h1, h2, 0, 20, 0.5);
        System.out.println(f);
    }
    */
}
