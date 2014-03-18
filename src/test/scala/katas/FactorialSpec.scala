package katas

import org.scalatest.{Matchers, FlatSpec}
/**
 *
 * User: sameer
 * Date: 18/03/2014
 * Time: 19:38
 */
class FactorialSpec extends FlatSpec with Matchers {

  "Factorial" should "give factorial of 1 as 1" in {
    Factorial of 1 should be (1)
  }

  it should "give factorial of 2 as 2" in {
    Factorial of 2 should be(2)
  }

  it should "give factorial of 3 as 6" in {
    Factorial of 3 should be(6)
  }



}
