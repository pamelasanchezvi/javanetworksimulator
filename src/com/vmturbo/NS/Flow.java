package com.vmturbo.NS;


public class Flow {

    public String sourceIP, destIP;
    public int sourcePort, destPort;
    public String protocol;

    public int size; //total bytes of the flow

    public Flow(String sourceIP, String destIP, int sourcePort, int destPort,
                    String protocol, int size) {
        this.sourceIP = sourceIP;
        this.destIP = destIP;
        this.sourcePort = sourcePort;
        this.destPort = destPort;
        this.protocol = protocol;
        this.size = size;
    }


}
