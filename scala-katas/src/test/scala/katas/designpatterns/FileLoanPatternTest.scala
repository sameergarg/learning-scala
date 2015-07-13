package katas.designpatterns

import katas.designpatterns.loan.FileLoanPattern
import org.scalatest.{Matchers, FlatSpec}

/**
 *
 */
class FileLoanPatternTest extends FlatSpec with Matchers{

  "FileLoanPattern" should "detect empty file" in new FileLoanPattern() {
    this.isEmpty() shouldBe true
  }
}
