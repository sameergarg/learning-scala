package katas.designpatterns

import java.math
import java.math.BigDecimal.valueOf

import katas.designpatterns.declarative.ImperativeVsDeclarative
import org.scalatest.{Matchers, FlatSpec}

/**
 *
 */
class ImperativeVsDeclarativeSpec extends FlatSpec with Matchers {

  "ImperativeVsDeclarative" should "calculateTotalOfDiscountedPrices_imperative" in new ImperativeVsDeclarative {
      this.calculateTotalOfDiscountedPrices_imperative(valueOf(20), math.BigDecimal.TEN) shouldBe math.BigDecimal.valueOf(750)
  }

  it should "calculateTotalOfDiscountedPrices_declarative" in new ImperativeVsDeclarative {
    this.calculateTotalOfDiscountedPrices_declarative(valueOf(20), math.BigDecimal.TEN) shouldBe math.BigDecimal.valueOf(750)
  }

}
