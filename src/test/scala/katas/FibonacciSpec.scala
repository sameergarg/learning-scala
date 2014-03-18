package katas

import org.scalatest.{Matchers, FlatSpec}

/**
 *
 * User: sameer
 * Date: 18/03/2014
 * Time: 20:48
 */
class FibonacciSpec extends FlatSpec with Matchers {

  "Fibonacci sequence number" should "be 1 at position 1" in {
    Fibonacci.after(1) should be(1)
  }
}
