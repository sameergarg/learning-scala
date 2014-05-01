package katas

import org.scalatest.{Matchers, FlatSpec, FunSuite}

class WWLTest extends FlatSpec with Matchers{

  it should "get last but one element of list" in {
    WWL lastButOneElementFromList List(1,2,3,4) should be(3)
    WWL lastButOneElementFromList List(1,2,3) should be(2)
  }

  it should "get last but one element of list using imperative" in {
    WWL lastButOneElementFromListUsingInbuiltMethod List(1,2,3,4) should be(3)
    WWL lastButOneElementFromListUsingInbuiltMethod List(1,2,3) should be(2)
  }

  it should "get nth element of the list" in {
    WWL nthElementOfList(List(1,2,3,4),2) should be(3)
    WWL nthElementOfListUsingDrop(List(1,2,3,4),3) should be(4)
  }

}
