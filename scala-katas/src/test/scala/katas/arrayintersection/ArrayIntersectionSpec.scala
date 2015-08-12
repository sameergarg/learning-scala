package katas.arrayintersection

import katas.arrayintersection.ArrayIntersection.intersection
import org.scalatest.{Matchers, FlatSpec}

/**
 *
 */
class ArrayIntersectionSpec extends FlatSpec with Matchers {

  "ArrayIntersection" should "have empty intersection when both array are empty" in {
    intersection(Array.empty[Int], Array.empty[Int]) shouldBe Nil
  }

  it should "return other array if one of them is empty" in {
    intersection(Array.empty[Int], Array(1,2)) shouldBe Array(1,2)
    intersection(Array(1,2), Array.empty[Int]) shouldBe Array(1,2)
  }

  it should "return non empty intersection of two array" in {
    intersection(Array(1,2,4,5,8,9), Array(2,3,5,7,8,10)) shouldBe Array(2,5,8)
  }

  it should "return the same array if both arrays are identical" in {
    intersection(Array(1,2,4,5,8,9), Array(1,2,4,5,8,9)) shouldBe Array(1,2,4,5,8,9)

  }
}
