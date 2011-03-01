package learn.scalaenum

import org.scalatest.{GivenWhenThen, FeatureSpec}

class EnumLearningSpec extends FeatureSpec with GivenWhenThen {
  feature("Scala Enum") {
    info("")

    scenario("") {
      given("Planet module is defined as Enumeration")
       object Planet extends Enumeration {
         //type Planet = Value // ?
         val Mercury, Venus, Earth, Mars, Jupiter, Saturn, Uranus, Neptune  = Value
       }



       //def
      then("member Planet is instanceof Planet.Value")
        assert(Planet.Mercury.isInstanceOf[Planet.Value])
        assert(Planet(2) == Planet.Earth)
        assert(Planet.Earth.id == 2)

      given("")

        object Currency extends Enumeration {
           val GBP = Value("GBP")
           val EUR = Value("EUR")
        }

      then("")
        assert(Currency.withName("GBP") == Currency.GBP)
    }
  }
}