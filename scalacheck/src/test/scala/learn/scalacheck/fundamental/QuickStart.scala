package learn.scalacheck.fundamental

import org.scalacheck._

object QuickStart extends Properties("Math") {
  property("sqrt") = Prop.forAll{
    (n:Int) => scala.Math.sqrt(n*n) == n
  }
}