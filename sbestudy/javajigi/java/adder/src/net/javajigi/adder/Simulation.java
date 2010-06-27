package net.javajigi.adder;

import java.util.concurrent.CopyOnWriteArrayList;

import net.javajigi.adder.action.AndGateAction;
import net.javajigi.adder.action.InverterAction;
import net.javajigi.adder.action.OrGateAction;

public class Simulation {
	private Integer currentTime = 0;
	
	private CopyOnWriteArrayList<WorkItem> agenda = new CopyOnWriteArrayList<WorkItem>();
	
	public Integer getCurrentTime() {
		return currentTime;
	}
	
	public void addWireListener(WireListener listener) {
		agenda.add(new WorkItem(currentTime+listener.getDelayTime(), listener));
	}
	
	public void run() {
		for (WorkItem workItem : agenda) {
			WireListener listener = workItem.getListener();
			currentTime = workItem.getTime();
			listener.listen();
		}
	}
	
	
	private class WorkItem {
		private int time;
		private WireListener listener;

		public WorkItem(int time, WireListener listener) {
			this.time = time;
			this.listener = listener;
		}
		
		public int getTime() {
			return time;
		}
		
		public WireListener getListener() {
			return listener;
		}
	};
	
	
	public void halfAdder(Wire input1, Wire input2, Wire sum, Wire carry) {
		Wire i = new Wire();
		Wire j = new Wire();
		
		Action orGate = new OrGateAction(input1, input2, i, this);
		orGate.action();
		
		Action andGate1 =  new AndGateAction(input1, input2, carry, this);
		andGate1.action();
		
		Action inverter = new InverterAction(carry, j, this);
		inverter.action();
		
		Action andGate2 =  new AndGateAction(i, j, sum, this);
		andGate2.action();		
	}
}
