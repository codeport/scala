package net.javajigi.adder.action;

import net.javajigi.adder.Action;
import net.javajigi.adder.Simulation;
import net.javajigi.adder.Wire;
import net.javajigi.adder.WireListener;

public class InverterAction implements Action {
	private Wire input;
	private Wire output;
	private Simulation simulation;

	public InverterAction(Wire input, Wire output, Simulation simulation) {
		this.input = input;
		this.output = output;
		this.simulation = simulation;
	}

	@Override
	public void action() {
		simulation.addWireListener(new InverterWireListener(input, output));
		input.addAction(this);
	}
	
	private class InverterWireListener implements WireListener{
		private static final int DELAY_TIME = 1;
		private Wire input;
		private Wire output;

		public InverterWireListener(Wire input, Wire output) {
			this.input = input;
			this.output = output;
		}
		
		@Override
		public void listen() {
			boolean inputSig = input.getSigVal();
			output.setSigVal(!inputSig);
		}
		
		@Override
		public int getDelayTime() {
			return DELAY_TIME;
		}
	};
}
