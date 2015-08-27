/**
 * @author shangshangchen
 */
package com.vmturbo.NS;

public class Flow {

    private Host source, dest;
    private int start, duration; //in seconds
    private double bandwidth; //bandwidth of the flow
    private double budget; // the budget the flow has, in virtual dollars

    private Path assignedPath; //an optional field for flow to remember its assignedPath

    //constructor: assuming infinite budget for now
    public Flow(Host source, Host dest, int start, int duration, double bandwidth /*, int budget*/) {
        this.source = source;
        this.dest = dest;
        this.start = start;
        this.duration = duration;
        this.bandwidth = bandwidth;
        this.budget = 200000.0; //Double.MAX_VALUE is problematic, so we use 200k to approximate infinity
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

    public double getBudget() {
        return budget;
    }

    @Override
    public String toString() {
        return ("flow: "
                + source.getName() + " -> " + dest.getName() + "; "
                + "starts: " + getStart() + "s; "
                + "duration: " + getDuration() + "s; "
                + bandwidth + " Gb/s; "
                + budget + " $;");
    }

    //This method allows flow to remember its path
    //It does NOT place the flow to path, which is done by placeFlow() in Path.java.
    public void setPath(Path path) {
        this.assignedPath = path;
    }

    public Path getPath() {
        return this.assignedPath;
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
