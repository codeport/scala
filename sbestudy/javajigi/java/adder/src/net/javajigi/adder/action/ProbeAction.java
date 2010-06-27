package net.javajigi.adder.action;

import net.javajigi.adder.Action;
import net.javajigi.adder.Simulation;
import net.javajigi.adder.Wire;

public class ProbeAction implements Action {
	private String name;
	private Wire wire;
	private Simulation simulation;


	public ProbeAction(String name, Wire wire, Simulation simulation) {
		this.name = name;
		this.wire = wire;
		this.simulation = simulation;
	}
	
	@Override
	public void action() {
		System.out.println(name + " " + simulation.getCurrentTime() + " new_value=" + wire.getSigVal());
		
		wire.addAction(this);
	}

}
