package net.javajigi.chapter10

import org.junit.Test
import org.junit.Assert._

import org.scalatest.junit.AssertionsForJUnit

class ForComprehensionsTest extends AssertionsForJUnit {
  @Test def for_yield() {
    val persons = new Person(38, "재성") :: new Person(38, "영정") :: new Person(10, "예은") :: new Person(8, "주한") :: Nil
    assertEquals("재성" :: "영정" :: Nil , persons filter(p => p.age > 15) map (p => p.name))
    assertEquals("재성" :: "영정" :: Nil , for(p <- persons if p.age > 15) yield p.name)
  }
}