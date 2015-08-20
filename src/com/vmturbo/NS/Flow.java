package com.vmturbo.NS;

public class Flow {

    String sourceIP, destIP;
    int sourcePort, destPort;
    String protocol;
    int size; //total bytes of the flow
    int budget; // the budge the flow has, in virtual dollars

    public Flow(String sourceIP, String destIP, int sourcePort, int destPort,
                    String protocol, int size, int budget) {
        this.sourceIP = sourceIP;
        this.destIP = destIP;
        this.sourcePort = sourcePort;
        this.destPort = destPort;
        this.protocol = protocol;
        this.size = size;
        this.budget = budget;
    }

    public String getSourceIP() {
        return sourceIP;
    }

    public String getDestIP() {
        return destIP;
    }

    public int getSourcePort() {
        return sourcePort;
    }

    public int getDestPort() {
        return destPort;
    }

    public String getProtocol() {
        return protocol;
    }

    public int getSize() {
        return size;
    }

    public int getBudget() {
        return budget;
    }


}
