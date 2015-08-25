/**
 *
 */
package com.vmturbo.NS.test;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import com.vmturbo.NS.Host;
import com.vmturbo.NS.Link;
import com.vmturbo.NS.SpineSwitch;
import com.vmturbo.NS.ToRSwitch;
import com.vmturbo.NS.TopologySetup;

/**
 * @author pamelasanchez
 *
 */
public class TopologySetupTests {

    String filename;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        filename = "input/topology";
    }

    /**
     * Test method for {@link com.vmturbo.NS.TopologySetup#parseFile()}.
     */
    @Test
    public void testParseFile() {
        TopologySetup topo1 = TopologySetup.getInstance();
        topo1.setTopologyFileName(filename);
        topo1.parseFile();
        ArrayList<Host> hostlist =  topo1.getHostList();
        ArrayList<ToRSwitch> torlist =  topo1.getToRSwitchList();
        ArrayList<SpineSwitch> spinelist =  topo1.getSpineSwitchList();
        ArrayList<Link> linklist =  topo1.getLinkList();
        System.out.println("in testParseFile");
        System.out.println(hostlist.size());
        for (Host host:hostlist ){

            System.out.println("host.getName()" + host.getName());
        }
        for (ToRSwitch tor:torlist){
            System.out.println("tor.getName()" + tor.getName());
        }
        for (SpineSwitch spine:spinelist ){
            System.out.println("spine.getName()" + spine.getName());
        }
        for (Link lk:linklist ){
            //System.out.println("spine.getName()" + lk.getName());
        }
      //  fail("Not yet implemented");
    }

}
