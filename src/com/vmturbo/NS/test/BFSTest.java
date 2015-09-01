/**
 * @author shangshangchen
 */
package com.vmturbo.NS.test;

import java.util.ArrayList;

import com.vmturbo.NS.BFS;
import com.vmturbo.NS.Node;
import com.vmturbo.NS.SpineSwitch;
import com.vmturbo.NS.ToRSwitch;

public class BFSTest {

    public static void main(String[] args) {


        ToRSwitch tor1 = new ToRSwitch("1");
        ToRSwitch tor2 = new ToRSwitch("2");
        ToRSwitch tor3 = new ToRSwitch("3");

        SpineSwitch spineA = new SpineSwitch("A");
        SpineSwitch spineB = new SpineSwitch("B");
        SpineSwitch spineC = new SpineSwitch("C");


        spineA.addtorSwitch(tor1);
        spineA.addtorSwitch(tor2);
        spineB.addtorSwitch(tor1);
        spineB.addtorSwitch(tor2);
        spineB.addtorSwitch(tor3);
        spineC.addtorSwitch(tor2);
        spineC.addtorSwitch(tor3);
        tor1.addSpine(spineA);
        tor1.addSpine(spineB);
        tor2.addSpine(spineA);
        tor2.addSpine(spineB);
        tor2.addSpine(spineC);
        tor3.addSpine(spineB);
        tor3.addSpine(spineC);

        //no need to construct links, because BFS doesn't use links.

        ArrayList<ToRSwitch> tors = new ArrayList<>();
        ArrayList<SpineSwitch> spines = new ArrayList<>();
        tors.add(tor1);
        tors.add(tor2);
        tors.add(tor3);
        spines.add(spineA);
        spines.add(spineB);
        spines.add(spineC);

        ArrayList<Node> allNodes = new ArrayList<>();
        allNodes.addAll(tors);
        allNodes.addAll(spines);

        BFS bfs = new BFS(allNodes);
        bfs.run();
        bfs.print();
    }


}
