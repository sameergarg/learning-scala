package functionalproginscala.ch05laziness

import org.scalatest.{Matchers, FunSuite}
import functionalproginscala.ch05laziness.Stream._

class StreamTest extends FunSuite with Matchers {

  test("headOption") {
    Stream(1, 2, 3, 4).headOption.get should be(1)
  }

  test("headOption_using_foldRight") {
    Stream(1, 2, 3, 4).headOption_using_foldRight.get should be(1)

    Empty.headOption_using_foldRight should be(None)
  }

  test("toList") {
    Stream(1, 2, 3, 4).toList should be(List(1, 2, 3, 4))
  }

  test("take") {
    Stream(1, 2, 3, 4).take(2).toList should be(List(1, 2))
    Stream(1, 2, 3, 4).take(1).toList should be(List(1))
    Stream(1, 2, 3, 4).take_unfold(2).toList should be(List(1, 2))
    Stream(1, 2, 3, 4).take_unfold(1).toList should be(List(1))
  }

  test("takeWhile") {
    Stream(1, 2, 3, 4).takeWhile(_ % 2 == 0).toList should be(List(2, 4))
    Stream(1, 2, 3, 4).takWhile_unfold(_ % 2 == 0).toList should be(List(2, 4))
  }

  test("exist") {
    Stream(1, 2, 3, 4).exists(_ % 3 == 0) should be(true)
  }

  test("forAll") {
    Stream(1, 2, 3, 4).forAll(_ > 0) should be(true)
    Stream(1, 2, 3, 4).forAll(_ < 0) should be(false)
  }

  test("takeWhile using foldRight") {
    Stream(1, 2, 3, 4).takeWhile(_ > 0).toList should be(List(1, 2, 3, 4))
    Stream(1, 2, 3, 4).takeWhile(_ < 0).toList should be(Nil)
  }

  test("map") {
    Stream(1, 2, 3, 4).map(_ + 1).toList should be(List(2, 3, 4, 5))
    Stream(1, 2, 3, 4).map_unfold(_ + 1).toList should be(List(2, 3, 4, 5))
  }

  test("append") {
    Stream(1, 2, 3, 4).append(Stream(5, 6, 7, 8)).toList should be(List(1, 2, 3, 4, 5, 6, 7, 8))
  }

  test("filter") {
    Stream(1, 2, 3, 4).filter(_ > 2).toList should be(List(3, 4))
  }

  test("flatMap") {
    Stream(1, 2, 3, 4).flatMap(a => Stream(a + 1)).toList should be(List(2, 3, 4, 5))
  }

  test("constant") {
    constant("a").take(3).toList should be(List("a", "a", "a"))
    constant_unfold("a").take(3).toList should be(List("a", "a", "a"))
  }

  test("from") {
    from(10).take(4).toList should be(List(10, 11, 12, 13))
    from_unfold(10).take(4).toList should be(List(10, 11, 12, 13))
  }

  test("fibs") {
    fibs.take(6).toList should be(List(0, 1, 1, 2, 3, 5))
    fibs_unfold.take(6).toList should be(List(0, 1, 1, 2, 3, 5))
  }

  test("unfold") {
    unfold(1)(s => Some((1, 1))).take(5).toList should be(List(1,1,1,1,1))
  }

}
