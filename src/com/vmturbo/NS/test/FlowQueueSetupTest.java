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
		Host h1 = new Host("h1");
        Host h2 = new Host("h2");
        Host h3 = new Host("h3");
        ToRSwitch tor1 = new ToRSwitch("tor1");
        ToRSwitch tor2 = new ToRSwitch("tor2");
        tor1.addHost(h1);
        tor1.addHost(h2);
        tor2.addHost(h3);
        h1.addtorSwitch(tor1);
        h2.addtorSwitch(tor1);
        h3.addtorSwitch(tor2);
        
        SpineSwitch spine = new SpineSwitch("spine");
        spine.addtorSwitch(tor1);
        spine.addtorSwitch(tor2);
        tor1.addSpine(spine);
        tor2.addSpine(spine);
        Link l1 = new Link(h1, tor1, 1, 0.5, Link.LinkType.HOSTTOTOR);
        Link l2 = new Link(tor1, h2, 1, 0.5, Link.LinkType.HOSTTOTOR);
        Link l3 = new Link(tor1, spine, 10, 2, Link.LinkType.TORTOSPINE);
        Link l4 = new Link(spine, tor2, 10, 2, Link.LinkType.TORTOSPINE);
        Link l5 = new Link(tor2, h3, 1, 0.5, Link.LinkType.HOSTTOTOR);

        topo = TopologySetup.getInstance();
        topo.getHostList().add(h1);
        topo.getHostList().add(h2);
        topo.getHostList().add(h3);
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
		String fileName = "input/flowqueue";
		FlowQueueSetup queueSetup = new FlowQueueSetup(fileName);
		queueSetup.populateQueue();
		ArrayList<Flow> queue = queueSetup.getFlowQueue();
		assertEquals("Size of queue is incorrect", queue.size(), 4);
		Flow flow = queue.get(0);
		if(flow == null){
			System.out.println("null");
		}
		assertEquals("Source Host of first flow is incorrect", queue.get(0).getSource().getName(), "h1");
		assertEquals("Source Host of second flow is incorrect", queue.get(1).getSource().getName(), "h1");
		assertEquals("Source Host of third flow is incorrect", queue.get(2).getSource().getName(), "h1");
		assertEquals("Source Host of fourth flow is incorrect", queue.get(3).getSource().getName(), "h1");
		assertEquals("Destination Host of first flow is incorrect", queue.get(0).getDest().getName(), "h2");
		assertEquals("Destination Host of second flow is incorrect", queue.get(1).getDest().getName(), "h2");
		assertEquals("Destination Host of third flow is incorrect", queue.get(2).getDest().getName(), "h2");
		assertEquals("Destination Host of fourth flow is incorrect", queue.get(3).getDest().getName(), "h2");
	}

	/**
	 * Test method for {@link com.vmturbo.NS.FlowQueueSetup#populateQueue()}.
	 */
	@Test
	public void testremoveFlow() {
		String fileName = "input/flowqueue";
		FlowQueueSetup queueSetup = new FlowQueueSetup(fileName);
		queueSetup.populateQueue();
		ArrayList<Flow> queue = queueSetup.getFlowQueue();
		assertEquals("Size of queue is incorrect", queue.size(), 4);
		queueSetup.removeFlow(queue.get(0));
		assertEquals("Size of queue is incorrect", queue.size(), 3);
		
		assertEquals("Source Host of first flow is incorrect", queue.get(0).getSource().getName(), "h1");
		assertEquals("Source Host of second flow is incorrect", queue.get(1).getSource().getName(), "h1");
		assertEquals("Source Host of third flow is incorrect", queue.get(2).getSource().getName(), "h1");
		assertEquals("Destination Host of first flow is incorrect", queue.get(0).getDest().getName(), "h2");
		assertEquals("Destination Host of second flow is incorrect", queue.get(1).getDest().getName(), "h2");
		assertEquals("Destination Host of third flow is incorrect", queue.get(2).getDest().getName(), "h2");
		
	}
	
}
