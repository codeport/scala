package net.javajigi.adder.action;

import net.javajigi.adder.Action;
import net.javajigi.adder.Simulation;
import net.javajigi.adder.Wire;
import net.javajigi.adder.WireListener;

public class AndGateAction implements Action {
	private Wire a1;
	private Wire a2;
	private Wire output;
	private Simulation simulation;

	public AndGateAction(Wire a1, Wire a2, Wire output, Simulation simulation) {
		this.a1 = a1;
		this.a2 = a2;
		this.output = output;
		this.simulation = simulation;
	}
	
	@Override
	public void action() {
		simulation.addWireListener(new AndGateWireListener(a1, a2, output));
		
		a1.addAction(this);
		a2.addAction(this);
	}
	
	private class AndGateWireListener implements WireListener {
		private static final int DELAY_TIME = 3;
		private Wire a1;
		private Wire a2;
		private Wire output;

		public AndGateWireListener(Wire a1, Wire a2, Wire output) {
			this.a1 = a1;
			this.a2 = a2;
			this.output = output;
		}
		
		@Override
		public void listen() {
			boolean a1Sig = a1.getSigVal();
			boolean a2Sig = a2.getSigVal();
			System.out.println("a1Sig : " + a1Sig + " a2Sig : " + a2Sig);
			output.setSigVal(a1Sig & a2Sig);
		}

		@Override
		public int getDelayTime() {
			return DELAY_TIME;
		}
		
	};
}
