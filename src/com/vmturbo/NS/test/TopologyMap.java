/**
 *
 */
package com.vmturbo.NS.test;

import com.vmturbo.NS.MapComponent;
import com.vmturbo.NS.MapShape;
import com.vmturbo.NS.MapNode;
import com.vmturbo.NS.ECMPPlacement;
import com.vmturbo.NS.Flow;
import com.vmturbo.NS.Node;
import com.vmturbo.NS.Host;
import com.vmturbo.NS.Link;
import com.vmturbo.NS.Path;
import com.vmturbo.NS.SpineSwitch;
import com.vmturbo.NS.ToRSwitch;
import com.vmturbo.NS.Utility;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * @author pamelasanchez
 * GUI for creating custom spine leaf topologies
 * and running different network algorithms
 *
 */
public class TopologyMap {

    /**
     * @param args
     */

    public static MapComponent mapPanel;
    public static MapShape mapShape;
    private static JButton leftButton;
    private static JFrame frame;
    private static JPanel panel;
    public static JTextField textFieldLinks;
    public static JTextField textFieldFlows;
    public static JTextField textResult;
    public static String numberOfLinks;
    public static String numberOfFlows;
    public static Integer flowsNum;

    //these global variables are for the flow algorithms
    public static ArrayList<MapNode> nodeList;
    public static ArrayList<Link> linksList;
    public static Double value;
    public static String str;
    public static ArrayList<Host> hosts;
    public static ArrayList<ToRSwitch> tors;
    public static ArrayList<SpineSwitch> spines;
    public static ArrayList<Link> links;
    public static ArrayList<Flow> curFlows;
    public static Host a;
    public static Host b;
    public static Host c;

    /**
     * This method creates the spine leaf topology
     * @param linkx determines the number of same index links
     * @return list of nodes to graph in GUI
     */

    public ArrayList<MapNode> TopoSetup(Integer linkx){
        ArrayList<MapNode> nodesToDraw = new ArrayList<>();
        int diagonalNum = linkx;
        String t_s = "20|20";
        ArrayList<String> torToSpineList = new ArrayList<String>();
        for ( int i = 0 ; i < diagonalNum ; i ++){
            torToSpineList.add(t_s);
        }

        //create 3 types of nodes

        a = new Host("a");
        b = new Host("b");
        c = new Host("c");

        ToRSwitch tor1 = new ToRSwitch("1");
        ToRSwitch tor2 = new ToRSwitch("2");
        ToRSwitch tor3 = new ToRSwitch("3");

        SpineSwitch spineA = new SpineSwitch("A");
        SpineSwitch spineB = new SpineSwitch("B");
        SpineSwitch spineC = new SpineSwitch("C");

        //create 4 lists to pass to ECMP
        hosts = new ArrayList<>();
        tors = new ArrayList<>();
        spines = new ArrayList<>();
        links = new ArrayList<>();
        hosts.add(a);
        hosts.add(b);
        hosts.add(c);
        tors.add(tor1);
        tors.add(tor2);
        tors.add(tor3);
        spines.add(spineA);
        spines.add(spineB);
        spines.add(spineC);
        //setting coordinates for gui
        int ctspine = 0 ;
        for( SpineSwitch node:spines){
            ctspine++;
            Double xcoord = 100.00 + ctspine*300.0;
            Double ycoord = 600.00;
            MapNode mapNode = new MapNode(node.getName(), xcoord , ycoord);
            node.x = xcoord;
            node.y = ycoord;
            nodesToDraw.add(mapNode);
        }
        int ctTor = 0 ;
        for( ToRSwitch node:tors){
            ctTor++;
            Double xcoord = 100.00 + ctTor*300.0;
            Double ycoord = 200.00;
            MapNode mapNode = new MapNode(node.getName(), xcoord , ycoord);
            node.x = xcoord;
            node.y = ycoord;
            nodesToDraw.add(mapNode);
        }
        int ctHosts = 0 ;
        for( Host node:hosts){
            ctHosts++;
            Double xcoord = 100.00 + ctHosts*300.0;
            Double ycoord = 100.00;
            MapNode mapNode = new MapNode(node.getName(), xcoord , ycoord);
            node.x = xcoord;
            node.y = ycoord;
            nodesToDraw.add(mapNode);
        }

        //connect nodes
        String h_t = "100|100";
        String[] arrayTS = new String[torToSpineList.size()];
        arrayTS = torToSpineList.toArray(arrayTS);
        Utility.connectNodes(a, tor1, new String[] {h_t}, links);
        Utility.connectNodes(b, tor2, new String[] {h_t}, links);
        Utility.connectNodes(c, tor3, new String[] {h_t}, links);


        Utility.connectNodes(spineA, tor1, arrayTS, links);
        Utility.connectNodes(spineA, tor2, new String[] {t_s}, links);
        Utility.connectNodes(spineA, tor3, new String[] {t_s}, links);

        Utility.connectNodes(spineB, tor1, new String[] {t_s}, links);
        Utility.connectNodes(spineB, tor2, arrayTS, links);
        Utility.connectNodes(spineB, tor3, new String[] {t_s}, links);

        Utility.connectNodes(spineC, tor1, new String[] {t_s}, links);
        Utility.connectNodes(spineC, tor2, new String[] {t_s}, links);
        Utility.connectNodes(spineC, tor3, arrayTS, links);

        linksList = links;

        return nodesToDraw;
    }


    /**
     * This method runs the ECMP algorithm
     * @param flowNumber
     * @return recommended Path
     */
    public Path ECMPAlgo(Integer flowNumber){
        Integer flows = flowNumber;

        System.out.println("int is " + flows);
        //===============run ECMP (1 host to 1 host) =================
        ECMPPlacement ecmp = new ECMPPlacement(spines, tors, hosts, links);
        Path path = null;
        //ecmp.printDistances();
        System.out.println("in ECMPAlgo() :");
        for (int i = 0; i < flowNumber; i++) {
            Flow flow = new Flow(a, c, 0, 10, 1);
            path = ecmp.recommendPath(flow);
            curFlows.add(flow);
            path.placeFlow(flow);
            //for gui drawing
            for (Link lnk: path.getLinks()){
                Node srcNode = lnk.getSrcNode();
                Node destNode = lnk.getDestNode();
                srcNode.paint = true;
                destNode.paint = true;
                System.out.println("  src:" + srcNode.getName() + " dest:" + destNode.getName());

            }
        }
        return path;

    }

    /**
     *
     * @param args
     * @throws NumberFormatException
     * @throws IOException
     */
    public static void main(String[] args) throws NumberFormatException, IOException

    {
        final TopologyMap newMap = new TopologyMap();
        nodeList = newMap.TopoSetup(6);
        curFlows = new ArrayList<Flow>();
        mapShape = new MapShape(40, nodeList, links); // ArrayList<MapNode> nodeList
        mapPanel = new MapComponent(mapShape);
        mapPanel.setPreferredSize(new Dimension(300,600));


    frame = new JFrame("Test");
    textFieldLinks = new JTextField(20);
    textFieldFlows = new JTextField(20);
    textResult = new JTextField(20);



    leftButton = new JButton("Run ECMP");
    leftButton.addActionListener(new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            numberOfLinks = textFieldLinks.getText();
            numberOfFlows = null;
            flowsNum = null;
            nodeList = null;

            numberOfFlows = textFieldFlows.getText();
            for(Flow flw:curFlows){
                flw.getPath().removeFlow(flw);
            }
            curFlows = new ArrayList<Flow>();

            System.out.println("---> curFlows size: "+ curFlows.size());

            //update list here
            nodeList = newMap.TopoSetup(Integer.parseInt(numberOfLinks));
            mapShape.UpdateMap(nodeList, links);
            System.out.println("---> nodelist size: "+ nodeList.size());
            System.out.println("---> linklist size: "+ links.size());
            for(Link lnk:links){
                Node from  = lnk.getSrcNode();
                Node to = lnk.getDestNode();
                from.paint = false;
                to.paint = false;
            }
            System.out.println("---> text is " + numberOfFlows);
            flowsNum = Integer.parseInt(numberOfFlows);
            System.out.println("---> int is " + flowsNum);
            newMap.ECMPAlgo(flowsNum);
            Utility.printLinkUsage(links, 0.001);
            mapPanel.repaint();

        }
    });

    panel = new JPanel();

    panel.setLayout(new FlowLayout());

    panel.add(leftButton);
    panel.add(textFieldLinks);
    panel.add(textFieldFlows);
    panel.add(textResult);


    frame.setLayout(new BorderLayout());
    frame.add(panel,BorderLayout.SOUTH);
    frame.add(mapPanel,BorderLayout.CENTER);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.pack();
    frame.setVisible(true);
    }


}
