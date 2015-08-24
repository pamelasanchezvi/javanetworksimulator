/**
 *
 */
package com.vmturbo.NS;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * @author pamelasanchez
 *
 */
public class TopologySetup {
    //
    ArrayList<SpineSwitch> spineList;
    ArrayList<ToRSwitch> torList;
    ArrayList<Host> hostList;
    ArrayList<Link> linkList;
    String fileName;

    String switchname;
    String switchType;
    String neighborsLine;
    String nextcapacity;
    String linkStr;
    String nextNeighbor;

    private static TopologySetup topoSetup = new TopologySetup();
    String line = null;

    private TopologySetup(){}

    public void setTopologyFileName(String fileToOpen){
        this.fileName = fileToOpen;
    }

    public static TopologySetup getInstance(){
        return topoSetup;
    }

// #switch name; type of switch; neighbor1, link capacity |  neighbor2, link capacity | ...
    public void parseFile(){
        try {
            FileReader filereader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(filereader);
            while((line = bufferedReader.readLine()) != null){
                String delims = ";";
                StringTokenizer str = new StringTokenizer(line, delims);
                while(str.hasMoreElements()){
                    switchname = str.nextToken();
                    switchType = str.nextToken();
                    neighborsLine = str.nextToken();
                }
                // error if name , type or neighbors line was null?
                initSwitches(neighborsLine, switchType, switchname);

            }
             bufferedReader.close();
        }
        catch (Exception e) {
            // TODO: handle exception
            System.out.println("couldn't open file");
        }
    }

    private void initSwitches(String linkPairs, String srcType, String srcName){
        // linkPair format is : " neighbor1, link capacity "
        switch (srcType) {
            case "spine":
                SpineSwitch newspine = new SpineSwitch(srcName);
                // look for neighbors
                parseNeighbors(linkPairs, newspine);
            case "tor":

                break;

            default:
                break;
        }
    }

    private void parseNeighbors(String linkp, SpineSwitch spswitch) {

        String delims = "|";
        StringTokenizer str = new StringTokenizer(neighborsLine, delims);
        while(str.hasMoreElements()){
            linkStr = str.nextToken();
            // here we pass String of format : " neighbor1, link capacity " to function
            // this is for each neighbor
            String delim = ",";
            StringTokenizer tk = new StringTokenizer(linkStr, delim);
            nextNeighbor = tk.hasMoreTokens() ? tk.nextToken() : null;
            nextcapacity = tk.hasMoreTokens() ? tk.nextToken() : null;
            Double capacity =  Double.parseDouble(nextcapacity);
            // then we create link between switchname and nextNeighbor
            ToRSwitch nextTor = new ToRSwitch(nextNeighbor);
            Link newlink = new Link(spswitch, nextTor, capacity, 0.0);
        }



    }

    public Host getHost(String hostName){
        for (Host host: hostList){
            if (host.getName().equals(hostName)){
                return host;
            }
        }
        return null;
    }



    // populate the list of hosts, list of , arraylist of spineswitches
}
