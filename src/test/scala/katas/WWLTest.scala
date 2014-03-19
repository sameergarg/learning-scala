package katas

import org.scalatest.{Matchers, FlatSpec, FunSuite}

class WWLTest extends FlatSpec with Matchers{

  it should "get last but one element of list" in {
    WWL lastButOneElementFromList List(1,2,3) should be(3)
  }

}
