package net.javajigi.adder;

import net.javajigi.adder.action.ProbeAction;

import org.junit.Before;
import org.junit.Test;

public class MySimulationTest {
	private Simulation simulation;
	private Wire input1;
	private Wire input2;
	private Wire sum;
	private Wire carry;

	@Before public void setup() {
		simulation = new Simulation();
		input1 = new Wire();
		input2 = new Wire();
		sum = new Wire();
		carry = new Wire();
	}
	
	@Test
	public void run() throws Exception {
		Action sumProbe = new ProbeAction("sum", sum, simulation);
		sumProbe.action();
		
		Action carryProbe = new ProbeAction("carry", carry, simulation);
		carryProbe.action();
		
		simulation.halfAdder(input1, input2, sum, carry);
		input1.setSigVal(true);
		simulation.run();
		
		input2.setSigVal(true);
		simulation.run();
	}
}
