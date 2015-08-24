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
    Integer capaciy;
    String linkStr;


    String line = null;

    public TopologySetup(String fileToOpen){
        this.fileName = fileToOpen;

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
                delims = "|";
                str = new StringTokenizer(neighborsLine, delims);
                while(str.hasMoreElements()){
                    linkStr = str.nextToken();

                }
            }
             bufferedReader.close();
        }
        catch (Exception e) {
            // TODO: handle exception
            System.out.println("couldn't open file");
        }
    }

    private void initSwitches(String linkPair){
        String delim = ",";
        StringTokenizer tk = new StringTokenizer(linkPair, delim);
        tk.nextToken();
    }
    // populate the list of hosts, list of , arraylist of spineswitches
}
