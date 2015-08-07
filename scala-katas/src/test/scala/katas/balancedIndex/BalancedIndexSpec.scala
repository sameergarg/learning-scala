package katas.balancedIndex

import org.scalatest.{Matchers, FlatSpec}
import BalancedIndex._

/**
 *
 */
class BalancedIndexSpec extends FlatSpec with Matchers {
  
  "BalancedIndex" should "find index when there are same number of match as mismatch" in {
    find(Array(1,2),1) shouldBe 1
    find(Array(),1) shouldBe -1
    find(Array(1),1) shouldBe -1
    find(Array(1,2,1),1) shouldBe 1
    find(Array(2,1,1,3,4),1) shouldBe 3
    find(Array(2,1,10,1,3,4),1) shouldBe 4
    find(Array(2,1,10,1,3,4),9) shouldBe -1
  }

  it should "determine if the number is even" in {
    def isEven(n: Int): Boolean = {
      (n.toByte & 1) == 0
    }

    isEven(2) shouldBe true
    isEven(1) shouldBe false
    isEven(333) shouldBe false
    isEven(24273648) shouldBe true
  }
}
