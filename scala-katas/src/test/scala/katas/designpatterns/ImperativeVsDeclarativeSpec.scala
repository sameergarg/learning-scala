package katas.designpatterns

import java.math
import java.math.BigDecimal.valueOf
import katas.designpatterns.iterator.Iterator
import org.scalatest.{Matchers, FlatSpec}

/**
 *
 */
class ImperativeVsDeclarativeSpec extends FlatSpec with Matchers {

  "Iterator" should "calculateTotalOfDiscountedPrices_imperative" in new Iterator {
      this.calculateTotalOfDiscountedPrices_imperative(valueOf(20), math.BigDecimal.TEN) shouldBe math.BigDecimal.valueOf(750)
  }

  it should "calculateTotalOfDiscountedPrices_declarative" in new Iterator {
    this.calculateTotalOfDiscountedPrices_declarative(valueOf(20), math.BigDecimal.TEN) shouldBe math.BigDecimal.valueOf(750)
  }

}
