/**
 * 
 */
package com.vmturbo.NS.test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import com.vmturbo.NS.Flow;
import com.vmturbo.NS.FlowQueueSetup;
import com.vmturbo.NS.Host;
import com.vmturbo.NS.Link;
import com.vmturbo.NS.SpineSwitch;
import com.vmturbo.NS.ToRSwitch;
import com.vmturbo.NS.TopologySetup;

/**
 * @author kunal
 *
 */
public class FlowQueueSetupTest {

	TopologySetup topo;
	
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		Host a = new Host("a");
        Host b = new Host("b");
        Host c = new Host("c");
        ToRSwitch tor1 = new ToRSwitch("tor1");
        ToRSwitch tor2 = new ToRSwitch("tor2");
        tor1.addHost(a);
        tor1.addHost(b);
        tor2.addHost(c);
        a.addtorSwitch(tor1);
        b.addtorSwitch(tor1);
        c.addtorSwitch(tor2);
        
        SpineSwitch spine = new SpineSwitch("spine");
        spine.addtorSwitch(tor1);
        spine.addtorSwitch(tor2);
        tor1.addSpine(spine);
        tor2.addSpine(spine);
        Link l1 = new Link(a, tor1, 1, 0.5);
        Link l2 = new Link(tor1, b, 1, 0.5);
        Link l3 = new Link(tor1, spine, 10, 2);
        Link l4 = new Link(spine, tor2, 10, 2);
        Link l5 = new Link(tor2, c, 1, 0.5);

        topo = TopologySetup.getInstance();
        topo.getHostList().add(a);
        topo.getHostList().add(b);
        topo.getHostList().add(c);
        topo.getLinkList().add(l1);
        topo.getLinkList().add(l2);
        topo.getLinkList().add(l3);
        topo.getLinkList().add(l4);
        topo.getLinkList().add(l5);
        topo.getSpineSwitchList().add(spine);
        topo.getToRSwitchList().add(tor1);
        topo.getToRSwitchList().add(tor2);       
	}

	/**
	 * Test method for {@link com.vmturbo.NS.FlowQueueSetup#populateQueue()}.
	 */
	@Test
	public void testPopulateQueue() {
		System.out.println(System.getProperty("topology"));
		String fileName = "../../../input/flowqueue";
		FlowQueueSetup queueSetup = new FlowQueueSetup(fileName);
		queueSetup.populateQueue();
		ArrayList<Flow> queue = queueSetup.getFlowQueue();
		assertEquals("test", queue.size(), 4);
		
		//fail("Not yet implemented");
	}

}
