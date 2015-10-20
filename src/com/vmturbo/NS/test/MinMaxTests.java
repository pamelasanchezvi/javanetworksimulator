/**
 *
 */
package com.vmturbo.NS.test;

import java.util.ArrayList;

import com.vmturbo.NS.ComputePaths;
import com.vmturbo.NS.MinMaxPlacement;
import com.vmturbo.NS.Flow;
import com.vmturbo.NS.Host;
import com.vmturbo.NS.Link;
import com.vmturbo.NS.SpineSwitch;
import com.vmturbo.NS.ToRSwitch;
import com.vmturbo.NS.Utility;

/**
 * @author pamelasanchez
 *
 */
public class MinMaxTests {

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        //create 3 types of nodes

        Host a = new Host("a");
        Host b = new Host("b");
        Host c = new Host("c");



        ToRSwitch tor1 = new ToRSwitch("1");
        ToRSwitch tor2 = new ToRSwitch("2");
        ToRSwitch tor3 = new ToRSwitch("3");

        SpineSwitch spineA = new SpineSwitch("A");
        SpineSwitch spineB = new SpineSwitch("B");
        SpineSwitch spineC = new SpineSwitch("C");

        //create 4 lists to pass to ECMP
        ArrayList<Host> hosts = new ArrayList<>();
        ArrayList<ToRSwitch> tors = new ArrayList<>();
        ArrayList<SpineSwitch> spines = new ArrayList<>();
        ArrayList<Link> links = new ArrayList<>();
        hosts.add(a);
        hosts.add(b);
        hosts.add(c);
        tors.add(tor1);
        tors.add(tor2);
        tors.add(tor3);
        spines.add(spineA);
        spines.add(spineB);
        spines.add(spineC);

        // list to pass to minmax
        ArrayList<Flow> flowlist = new ArrayList<Flow>();

        //connect nodes
        String h_t = "100|100";
        String t_s = "20|20";
        Utility.connectNodes(a, tor1, new String[] {h_t}, links);
        Utility.connectNodes(b, tor2, new String[] {h_t}, links);
        Utility.connectNodes(c, tor3, new String[] {h_t}, links);

        Utility.connectNodes(spineA, tor1, new String[] {t_s, t_s}, links);
        Utility.connectNodes(spineA, tor2, new String[] {t_s}, links);
        Utility.connectNodes(spineA, tor3, new String[] {t_s}, links);

        Utility.connectNodes(spineB, tor1, new String[] {t_s}, links);
        Utility.connectNodes(spineB, tor2, new String[] {t_s, t_s}, links);
        Utility.connectNodes(spineB, tor3, new String[] {t_s}, links);

        Utility.connectNodes(spineC, tor1, new String[] {t_s}, links);
        Utility.connectNodes(spineC, tor2, new String[] {t_s}, links);
        Utility.connectNodes(spineC, tor3, new String[] {t_s, t_s}, links);

        /**
        //===============run ECMP (n hosts to n hosts) =================
        int numFlows = 12;
        ArrayList<Host> as = new ArrayList<>();
        for (int i = 0; i < numFlows; i++) {
            Host h = new Host("a" + i);
            Utility.connectNodes(h, tor1, new String[] {h_t}, links);
            as.add(h);
        }

        ArrayList<Host> cs = new ArrayList<>();
        for (int i = 0; i < numFlows; i++) {
            Host h = new Host("c" + i);
            Utility.connectNodes(h, tor3, new String[] {h_t}, links);
            cs.add(h);
        }

        hosts.addAll(as);
        hosts.addAll(cs);

        ECMPPlacement ecmp = new ECMPPlacement(spines, tors, hosts, links);
        //ecmp.printDistances();
        for (int i = 0; i < numFlows; i++) {
            Flow flow = new Flow(as.get(i), cs.get(i), 0, 10, 1);
            Path path = ecmp.recommendPath(flow);
            //System.out.println("\n"+ path + "\n");
            path.placeFlow(flow);
        }
        */

        /**
        //===============run ECMP (1 host to 1 host) =================
        ECMPPlacement ecmp = new ECMPPlacement(spines, tors, hosts, links);
        //ecmp.printDistances();
        for (int i = 0; i < 30; i++) {
            Flow flow = new Flow(a, c, 0, 10, 1);
            Path path = ecmp.recommendPath(flow);
            path.placeFlow(flow);
        }
        */


        /**
        //==============run economic (n hosts to n hosts)================
        int numFlows = 30;
        ArrayList<Host> as = new ArrayList<>();
        for (int i = 0; i < numFlows; i++) {
            Host h = new Host("a" + i);
            Utility.connectNodes(h, tor1, new String[] {"5|5"}, links);
            as.add(h);
        }

        ArrayList<Host> cs = new ArrayList<>();
        for (int i = 0; i < numFlows; i++) {
            Host h = new Host("c" + i);
            Utility.connectNodes(h, tor3, new String[] {"5|5"}, links);
            cs.add(h);
        }

        hosts.addAll(as);
        hosts.addAll(cs);

        ComputePaths cmp = new ComputePaths(spines, tors, hosts, links);
        cmp.findPaths();
        for (int i = 0; i < numFlows; i++) {
            Flow flow = new Flow(as.get(i), cs.get(i), 0, 10, 1);
            Path path = EconomicPlacement.econPlacement(flow, cmp.getPaths(as.get(i), cs.get(i)));
            //System.out.println("\n"+ path + "\n");
            path.placeFlow(flow);
        }
        */

        Utility.printLinkUsage(links, 0.001);
        /**
         * run minmax
         */
        ComputePaths cmp = new ComputePaths(spines, tors, hosts, links);
        cmp.findPaths();
        for (int i = 0; i < 30; i++) {
            Flow flow = new Flow(a, c, 0, 10, 1);
            flowlist.add(flow);
        }
        MinMaxPlacement.minmaxPlacement(flowlist, cmp.getPaths(a, c));
        Utility.printLinkUsage(links, 0.001);
    }




}
