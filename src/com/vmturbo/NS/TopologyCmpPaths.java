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
public class TopologyCmpPaths {
    // extended for path creation
    ArrayList<Path> pathsList;
    ArrayList<ArrayList<Link>> prepathsList;

    // lists for the entire network
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


    private static TopologyCmpPaths topoCmpPaths = new TopologyCmpPaths();
    String line = null;

    private TopologyCmpPaths(){
        spineList = new ArrayList<SpineSwitch>();
        torList = new ArrayList<ToRSwitch>();
        hostList = new ArrayList<Host>();
        linkList = new ArrayList<Link>();
        pathsList = new ArrayList<Path>();
        prepathsList = new ArrayList<ArrayList<Link>>();
    }
    public void setTopologyFileName(String fileToOpen){
        this.fileName = fileToOpen;
    }

    public static TopologyCmpPaths getInstance(){
        return topoCmpPaths;
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
                String delims = ";";
                StringTokenizer str = new StringTokenizer(line, delims);
                while(str.hasMoreElements()){
                    switchname = str.nextToken().trim();
                    switchType = str.nextToken().trim();
                    neighborsLine = str.nextToken().trim();
                }
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

    private Link linkSearch(String src, String dest){
        for (Link nxt: linkList){
            if (nxt.exists(src, dest)){
                return nxt;
            }
        }
        return null;
    }

    private Link prePathSearch(ArrayList<Link> prepath){
        for (Link nxt: prepath){
            if (nxt.exists(src, dest)){
                return nxt;
            }
        }
        return null;
    }

    /**
     *  initializes switch objects as needed:
     *
     *  @param linkPairs - string list of neighbors to the switch described by this line
     *  @param srcType - type of switch we are creating in this function
     *  @param srcName - name of switch we are creating in this function
     */
    private void initSwitches(String linkPairs, String srcType, String srcName){
        switch (srcType) {
            case "spine":
                SpineSwitch newspine=null;
                Boolean isExistingSpine = false;
                if ((newspine = spineSearch(srcName)) == null) {
                    newspine = new SpineSwitch(srcName);
                    spineList.add(newspine);
                    isExistingSpine = false;
                }else {
                    isExistingSpine = true;
                }
                // look for neighbors and pass information that spine already exists
                parseSpineNeighbors(linkPairs, newspine, isExistingSpine);
                break;
            case "tor":
                ToRSwitch newtor = null;
                if ((newtor = torSearch(srcName)) == null) {
                    newtor = new ToRSwitch(srcName);
                    torList.add(newtor);
                }
                // look for neighbors
                parseLeafToRNeighbors(linkPairs, newtor);
                break;

            default:
                break;
        }
    }
    /**
     *  for each newline containing spine's information:
     *  takes the spineswitch neighbor list,
     *  adds new tor switches to global torlist
     *  adds new tor switches to spine object's torlist
     */
    private void parseSpineNeighbors(String linkp, SpineSwitch spswitch, Boolean isExistingSpine) {
        Link newlinkTo = null;
        Link newlinkFrom = null;
        // if isExistingSpine is true look for links same as new one among the spines outgoing links

        String delims = "|";
        StringTokenizer str = new StringTokenizer(linkp, delims);
        //for each neighbor:
        while(str.hasMoreElements()){
            linkStr = str.nextToken().trim();
            // here we pass String of format : " neighbor1, neighbor type, link capacity " to function
            // this is for each neighbor
            String delim = ",";
            StringTokenizer tk = new StringTokenizer(linkStr, delim);
            nextNeighbor = tk.hasMoreTokens() ? tk.nextToken().trim() : null;
            nextType = tk.hasMoreTokens() ? tk.nextToken().trim() : null;
            nextcapacity = tk.hasMoreTokens() ? tk.nextToken().trim() : null;
            Double capacity =  Double.parseDouble(nextcapacity);
            // then we create object if we can't find the nextNeighbor in the list of ToRs

            ToRSwitch nextTor = null;
            if ((nextTor = torSearch(nextNeighbor)) == null){
                nextTor = new ToRSwitch(nextNeighbor);
                torList.add(nextTor);
                spswitch.addtorSwitch(nextTor);
                if(isExistingSpine){
                    // TODO extend prepath along spine
                }else{
                    // nothing
                }
                //we know this will be a brand new link, TODO * new to the linklist and * new to the prepaths
            }else{
                spswitch.addtorSwitch(nextTor);
                //TODO  check link doesn't already exist and add to list of links, check if there are paths including this link already
                // capacity??
                if (isExistingSpine){  // and we already know  tor exists
                    linkSearchExtended(spswitch, nextTor, "0.0",newlinkTo);
                }else{
                    // TODO extend prepath along ToR
                }

            }
        }
    }

    /**
     *  for each newline containing ToR's information:
     *  takes the ToRswitch neighbor list,
     *  adds new host blades to global hostlist
     *  adds new host blades to specific ToR object's hostlist
     *
     *  @param linkp
     *  @param torswitch
     *  @return void
     */
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

                    torswitch.addSpine(nextsp); // check if spine already on list spine switched  in ToR object
                    if ((newlink = linkSearch(torswitch.getName(), nextsp.getName())) == null){
                        newlink = new Link(torswitch, nextsp, capacity, 0.0, Link.LinkType.TORTOSPINE);
                         linkList.add(newlink);
                    }
                    break;
                case "host":
                    Host nextHost = null;
                    if ( (nextHost = hostSearch(nextNeighbor))== null){
                        nextHost = new Host(nextNeighbor);
                        nextHost.addtorSwitch(torswitch);
                        hostList.add(nextHost);
                    }

                    torswitch.addHost(nextHost);// check if spine already on list spine switched  in ToR object
                    if ((newlink = linkSearch(torswitch.getName(), nextHost.getName())) == null){
                        newlink = new Link(torswitch, nextHost, capacity, 0.0, Link.LinkType.HOSTTOTOR);
                         linkList.add(newlink);
                    }
                    newlink = null;
                    if ((newlink = linkSearch(nextHost.getName(), torswitch.getName())) == null){
                        newlink = new Link(nextHost, torswitch, capacity, 0.0, Link.LinkType.HOSTTOTOR);
                         linkList.add(newlink);
                    }
                    break;
                default:
                    break;
            }



        }



    }

    /**
     * method copies a path as a list of link and creates a path object
     * @return a path object if the resulting path indeed has host endpoints
     *
     */
    private boolean copyPath(ArrayList<Link> pathOrig,ArrayList<Link> pathCpy){
        boolean newpathCreated = false;
        for (Link lnk:pathOrig){
            pathCpy.add(lnk);
        }
        Node srcNode = pathCpy.get(0).getSrcNode();
        Node destNode = pathCpy.get(pathCpy.size()-1).getSrcNode();
        if(srcNode instanceof Host && destNode instanceof Host){
            Path newPath = new Path((Host)srcNode, (Host)destNode, pathCpy);
            pathsList.add(newPath);
            newpathCreated = true;
        }else{
            prepathsList.add(pathCpy);
            newpathCreated = false;
        }
        return newpathCreated;
    }

    /**
     * @return linkExists
     */
    public Boolean linkSearchExtended(Node from , Node to, String capacity, Link potentialLink){
        boolean linkExists =  false;
        //check link doesn't already exist and add to list of links, check if there are paths including this link already
        if ((potentialLink = linkSearch(from.getName(), to.getName())) == null){
            potentialLink = new Link(from, to, 0.0, Link.LinkType.TORTOSPINE);
             linkList.add(potentialLink);
            // TODO add link to prepaths
             // TODO if any nodes in common with any existing prepath , add link to extend existing prepaths
        }else{
            linkExists = true;
        }
        // TODO change below !
        // then we create link between switchname and nextNeighbor
        // in this case we expect there are more than one connections between a given spine and tor
        Node curSrc = null;
        Node curDest = null;
        String srcName = null;
        String destName = null;
        String spineName = null;
        String torName = null;
        newlinkTo = new Link(spswitch, nextTor, capacity, 0.0, Link.LinkType.TORTOSPINE);
        linkList.add(newlinkTo);
        //TODO  check link doesn't already exist and add as prepath

        /*
         * loop through all current partial paths and look at endpoints
         */
        /*
         * looking to add " spine to ToR " link at the beginning and end of the path
         */
        ArrayList<Link> newpath  = null;
        for (ArrayList<Link> path: prepathsList){
            Link firstlink = path.get(0);
            Link lastlink = path.get(path.size() -1);
            if(path.size() >= 1){
                curSrc = firstlink.getSrcNode();
                srcName = curSrc.getName();
                curDest = firstlink.getDestNode();
                if (nextNeighbor.equals(srcName)){
                 // ADDING TO BEGINNING
                 // current path and new link have common node , src of current path is common node
                 // initialize newpath
                    newpath = new ArrayList<Link>();
                 // copy links to new path
                    copyPath(path, newpath);
                }
                curDest = lastlink.getDestNode();
                destName = curDest.getName();
                spineName = spswitch.getName();
                if (spineName.equals(destName)){
                 // ADDING TO END
                 // current path and new link have common node , src of current path is common node
                 // initialize newpath
                    newpath = new ArrayList<Link>();
                 // copy links to new path
                    copyPath(path, newpath);
                }
            }
        }
        newlinkFrom = new Link(nextTor, spswitch, capacity, 0.0, Link.LinkType.TORTOSPINE);
        linkList.add(newlinkFrom);
        /*
         * looking to add "ToR to spine" link at the beginning and end of the path
         */
        for (ArrayList<Link> path: prepathsList){
            Link firstlink = path.get(0);
            Link lastlink = path.get(path.size() -1);
            if(path.size() >= 1){
                curSrc = firstlink.getSrcNode();
                srcName = curSrc.getName();
                spineName = spswitch.getName();
                if (spineName.equals(srcName)){
                 // ADDING TO BEGINNING
                 // current path and new link have common node , src of current path is common node
                 // initialize newpath
                    newpath = new ArrayList<Link>();
                 // copy links to new path
                    copyPath(path, newpath);
                }
                curDest = lastlink.getDestNode();
                destName = curDest.getName();
                torName = nextNeighbor;
                if (torName.equals(destName)){
                 // ADDING TO END
                 // current path and new link have common node , src of current path is common node
                 // initialize newpath
                    newpath = new ArrayList<Link>();
                 // copy links to new path
                    copyPath(path, newpath);
                }
            }
        }
        return  linkExists;
    }

    /**
     * method returns Host object for a given host name
     *
     * @param hostName
     * @return Host object
     */
    public Host getHost(String hostName){
        for (Host host: hostList){
            if (host.getName().equals(hostName)){
                return host;
            }
        }
        return null;
    }



}
