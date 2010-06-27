package net.javajigi.chapter11

abstract class BasicCircuitSimulation extends Simulation {
  def InverterDelay: Int
  def AndGateDelay: Int
  def OrGateDelay: Int

  class Wire {
    private var sigVal = false
    private var actions: List[Action] = List()

    def getSignal = sigVal

    def setSignal(s: Boolean) =
      if (s != sigVal) {
        sigVal = s
        actions foreach (action => action())
      }

    def addAction(a: Action) = {
      actions = a :: actions
      println("triggering added!!")
      a()
    }
  }

  def inverter(input: Wire, output: Wire) = {
    def invertAction() {
      val inputSig = input.getSignal
      println("inverter : inputSig : " + inputSig)
      afterDelay(InverterDelay) {
        println("triggering inverter afterDelay")
        output setSignal !inputSig
      }
    }
    input addAction invertAction
  }

  def andGate(a1: Wire, a2: Wire, output: Wire) = {
    def andAction() = {
      val a1Sig = a1.getSignal
      val a2Sig = a2.getSignal
      println("andAction : a1Sig : " + a1Sig + " a2Sig : " + a2Sig)
      afterDelay(AndGateDelay) {
        println("triggering andGate afterDelay")
        output setSignal (a1Sig & a2Sig)
      }
    }
    a1 addAction andAction
    a2 addAction andAction
  }

  def orGate(o1: Wire, o2: Wire, output: Wire) {
    def orAction() {
      val o1Sig = o1.getSignal
      val o2Sig = o2.getSignal
      println("orAction: o1Sig : " + o1Sig + " o2Sig : " + o2Sig)
      afterDelay(OrGateDelay) {
        println("triggering orGate afterDelay")
        output setSignal (o1Sig | o2Sig)
      }
    }
    o1 addAction orAction
    o2 addAction orAction
  }

  def probe(name: String, wire: Wire) {
    def probeAction() {
      println(name +" "+ currentTime + " new-value = "+ wire.getSignal)
    }
    
    wire addAction probeAction
  }
}