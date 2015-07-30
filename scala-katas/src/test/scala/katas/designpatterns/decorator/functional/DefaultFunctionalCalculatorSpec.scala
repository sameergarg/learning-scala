package katas.designpatterns.decorator.functional

import org.scalatest.{Matchers, FlatSpec}

/**
 *
 */
class DefaultFunctionalCalculatorSpec extends FlatSpec with Matchers {

  "FunctionalCalculator" should "add two numbers" in new DefaultFunctionalCalculator() {
      //add.calculate(1 ,2) shouldBe 3
  }

  it should "log the add operation" in new DefaultFunctionalCalculator {
    //loggingCalculator(add, 1, 2) shouldBe 3
  }
}
