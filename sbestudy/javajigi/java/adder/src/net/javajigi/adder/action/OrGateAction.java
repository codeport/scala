package net.javajigi.adder.action;

import net.javajigi.adder.Action;
import net.javajigi.adder.Simulation;
import net.javajigi.adder.Wire;
import net.javajigi.adder.WireListener;

public class OrGateAction implements Action {
	private Wire o1;
	private Wire o2;
	private Wire output;
	private Simulation simulation;

	public OrGateAction(Wire o1, Wire o2, Wire output, Simulation simulation) {
		this.o1 = o1;
		this.o2 = o2;
		this.output = output;
		this.simulation = simulation;
	}
	
	@Override
	public void action() {
		simulation.addWireListener(new OrGateWireListener(o1, o2, output));
		
		o1.addAction(this);
		o2.addAction(this);
	}
	
	private class OrGateWireListener implements WireListener {
		private static final int DELAY_TIME = 5;
		private Wire o1;
		private Wire o2;
		private Wire output;

		public OrGateWireListener(Wire o1, Wire o2, Wire output) {
			this.o1 = o1;
			this.o2 = o2;
			this.output = output;
		}
		
		@Override
		public void listen() {
			boolean o1Sig = o1.getSigVal();
			boolean o2Sig = o2.getSigVal();
			System.out.println("o1Sig : " + o1Sig + " o2Sig : " + o2Sig);
			output.setSigVal(o1Sig | o2Sig);
		}

		@Override
		public int getDelayTime() {
			return DELAY_TIME;
		}
	};
}
