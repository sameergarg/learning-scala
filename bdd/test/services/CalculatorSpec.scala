package services

import org.scalatest.{GivenWhenThen, FeatureSpec, Matchers, WordSpec}

/**
 * Demonstrate use of Scala test for BDD
 */
class CalculatorSpec extends FeatureSpec with GivenWhenThen with Matchers with Calculator {

  info("Demonstrate how unit tests can be written in BDD format using cucumber scenarios")

  feature("Calculator") {

    scenario("Multiply two numbers") {

      Given("the digit 2")
        val op1 = 2

      When("it is multiplied by 3")
        val op2 = 3
        val operator = '*'
        val result = calculate(op1, op2, operator)

      Then("the result is 6")
        result should be(6)
    }
  }
}
