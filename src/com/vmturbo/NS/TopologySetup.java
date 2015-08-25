

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
    String nextType;
    String linkStr;
    String nextNeighbor;

    private static TopologySetup topoSetup = new TopologySetup();
    String line = null;

    private TopologySetup(){
        spineList = new ArrayList<SpineSwitch>();
        torList = new ArrayList<ToRSwitch>();
        hostList = new ArrayList<Host>();
        linkList = new ArrayList<Link>();
    }

    public void setTopologyFileName(String fileToOpen){
        this.fileName = fileToOpen;
    }

    public static TopologySetup getInstance(){
        return topoSetup;
    }

    public String getFileName(){
        return fileName;
    }

    public ArrayList<Host> getHostList(){
        return hostList;
    }

    public ArrayList<ToRSwitch> getToRSwitchList(){
        return torList;
    }

    public ArrayList<SpineSwitch> getSpineSwitchList(){
        return spineList;
    }

    public ArrayList<Link> getLinkList(){
        return linkList;
    }
    // #switch name; type of switch; neighbor1, link capacity |  neighbor2, link capacity | ...

    public void parseFile(){
        try {
            FileReader filereader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(filereader);
            while((line = bufferedReader.readLine()) != null){
                System.out.println("going line by line" + line);
                String delims = ";";
                StringTokenizer str = new StringTokenizer(line, delims);
                while(str.hasMoreElements()){
                    switchname = str.nextToken().trim();
                    switchType = str.nextToken().trim();
                    neighborsLine = str.nextToken().trim();
                }
                System.out.println("before initSwitches");
                // error if name , type or neighbors line was null?
                initSwitches(neighborsLine, switchType, switchname);

            }
            bufferedReader.close();
        }
        catch (Exception e) {
            System.out.println("couldn't open file");
        }
    }

    private Host hostSearch(String host){
        for (Host nxt: hostList){
            if (nxt.exists(host)){
                return nxt;
            }
        }
        return null;
    }
    private ToRSwitch torSearch(String tor){
        for (ToRSwitch nxt: torList){
            if (nxt.exists(tor)){
                return nxt;
            }
        }
        return null;
    }
    private SpineSwitch spineSearch(String spine){
        for (SpineSwitch nxt : spineList){
            if (nxt.exists(spine)){
                return nxt;
            }
        }
        return null;
    }
    private void initSwitches(String linkPairs, String srcType, String srcName){
        // linkPair format is : " neighbor1, link capacity "
        System.out.println("initSwitches cases" + srcType);
        switch (srcType) {
            case "spine":
                System.out.println("case spine");
                SpineSwitch newspine=null;

                if ((newspine = spineSearch(srcName)) == null) {
                    newspine = new SpineSwitch(srcName);
                    System.out.println("case spine adding");
                    spineList.add(newspine);
                }
                // look for neighbors
                parseSpineNeighbors(linkPairs, newspine);
                break;
            case "tor":
                System.out.println("case tor");
                ToRSwitch newtor = null;
                if ((newtor = torSearch(srcName)) == null) {
                    newtor = new ToRSwitch(srcName);
                    System.out.println("case tor adding");
                    torList.add(newtor);
                }
                parseLeafToRNeighbors(linkPairs, newtor);
                break;

            default:
                break;
        }
    }

    private void parseSpineNeighbors(String linkp, SpineSwitch spswitch) {

        String delims = "|";
        StringTokenizer str = new StringTokenizer(neighborsLine, delims);
        while(str.hasMoreElements()){
            linkStr = str.nextToken().trim();
            // here we pass String of format : " neighbor1, link capacity " to function
            // this is for each neighbor
            String delim = ",";
            StringTokenizer tk = new StringTokenizer(linkStr, delim);
            nextNeighbor = tk.hasMoreTokens() ? tk.nextToken().trim() : null;
            nextType = tk.hasMoreTokens() ? tk.nextToken().trim() : null;
            nextcapacity = tk.hasMoreTokens() ? tk.nextToken().trim() : null;
            Double capacity =  Double.parseDouble(nextcapacity);
            // then we create link between switchname and nextNeighbor
            ToRSwitch nextTor = null;
            if ((nextTor = torSearch(nextNeighbor)) == null){
                nextTor = new ToRSwitch(nextNeighbor);
                torList.add(nextTor);
            }
            Link newlink = new Link(spswitch, nextTor, capacity, 0.0, Link.LinkType.TORTOSPINE);
            linkList.add(newlink);
        }



    }

    private void parseLeafToRNeighbors(String linkp, ToRSwitch torswitch) {

        String delims = "|";
        StringTokenizer str = new StringTokenizer(neighborsLine, delims);
        while(str.hasMoreElements()){
            linkStr = str.nextToken().trim();
            // here we pass String of format : " neighbor1, type, link capacity " to function
            // this is for each neighbor
            String delim = ",";
            StringTokenizer tk = new StringTokenizer(linkStr, delim);
            nextNeighbor = tk.hasMoreTokens() ? tk.nextToken().trim() : null;
            nextType = tk.hasMoreTokens() ? tk.nextToken().trim() : null;
            nextcapacity = tk.hasMoreTokens() ? tk.nextToken().trim() : null;
            Double capacity =  Double.parseDouble(nextcapacity);
            // then we create link between switchname and nextNeighbor
            Link newlink = null;
            switch (nextType) {
                case "spine":
                    SpineSwitch nextsp = null;
                    if ( (nextsp = spineSearch(nextNeighbor))== null){
                        nextsp = new SpineSwitch(nextNeighbor);
                        spineList.add(nextsp);
                    }
                    newlink = new Link(torswitch, nextsp, capacity, 0.0, Link.LinkType.TORTOSPINE);
                    linkList.add(newlink);
                    break;
                case "host":
                    Host nextHost = null;
                    if ( (nextHost = hostSearch(nextNeighbor))== null){
                        nextHost = new Host(nextNeighbor);
                        hostList.add(nextHost);
                    }
                    newlink = new Link(torswitch, nextHost, capacity, 0.0, Link.LinkType.HOSTTOTOR);
                    linkList.add(newlink);
                    break;
                default:
                    break;
            }



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

