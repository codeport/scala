package net.javajigi.adder;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

public class Wire {
	private boolean sigVal = false;
	private CopyOnWriteArrayList<Action> actions = new CopyOnWriteArrayList<Action>();
	
	public boolean getSigVal() {
		return sigVal;
	}
	
	public void setSigVal(boolean newSigVal) {
		if( newSigVal != sigVal ) {
			sigVal = newSigVal;
			Iterator<Action> actIter = actions.iterator();
			while(actIter.hasNext()) {
				Action action = actIter.next();
				System.out.println("execute action : " + action.getClass().getCanonicalName());
				action.action();
			}
		}
	}
	
	public void addAction(Action action) {
		actions.add(action);
	}
}
