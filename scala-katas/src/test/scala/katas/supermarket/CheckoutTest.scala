package katas.supermarket

import org.scalatest.{ Matchers, FunSuite}

class CheckoutTest extends FunSuite with Matchers {

  private val checkout = new Checkout()


  test("should scan oranges and apply 3 for 2 offer") {
    checkout totalFor List("Orange") should be (25)
    checkout totalFor List("Orange", "Orange", "Orange") should be (50)
  }


  test("should scan apples and apply buy 1 get 1 offer") {
    checkout totalFor List("Apple") should be (60)
    checkout totalFor List("Apple", "Apple") should be (60)
  }


  test("should scan apples and oranges and apply offers") {
    checkout totalFor List("Orange", "Orange", "Orange","Apple","Apple") should be (110)
    checkout totalFor List("Orange", "Orange", "Orange","Apple") should be (110)
  }

  test("should scan bananas and apply buy 1 get 1 offer") {
    checkout totalFor List("Banana") should be (100)
    checkout totalFor List("Banana", "Banana") should be (100)
  }

  test("should scan apples and bananas and apply buy two get cheapest free offer") {
    checkout totalFor List("Apple","Banana") should be (100)
    checkout totalFor List("Apple","Banana","Banana") should be (160)
    checkout totalFor List("Apple","Apple","Banana","Banana") should be (160)
  }

  test("should scan melons without offer") {
    checkout totalFor List("Melon","Melon") should be(400)
  }
}
