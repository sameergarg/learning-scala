package functionalproginscala.ch05laziness

import org.scalatest.{Matchers, FunSuite}

class StreamTest extends FunSuite with Matchers {

  test("headOption") {
    Stream(1,2,3,4).headOption.get should be(1)
  }

  test("headOption_using_foldRight") {
    Stream(1,2,3,4).headOption_using_foldRight.get should be(1)

    Empty.headOption_using_foldRight should be(None)
  }

  test("toList") {
      Stream(1,2,3,4).toList should be(List(1,2,3,4))
  }

  test("take") {
    Stream(1,2,3,4).take(2).toList should be(List(1,2))
    Stream(1,2,3,4).take(1).toList should be(List(1))
  }

  test("takeWhile") {
    Stream(1,2,3,4).takeWhile(_%2==0).toList should be(List(2,4))
  }

  test("exist") {
    Stream(1,2,3,4).exists(_%3==0) should be(true)
  }

  test("forAll") {
    Stream(1,2,3,4).forAll(_>0) should be(true)
    Stream(1,2,3,4).forAll(_<0) should be(false)
  }

  test("takeWhile using foldRight") {
    Stream(1,2,3,4).takeWhile(_>0).toList should be(List(1,2,3,4))
    Stream(1,2,3,4).takeWhile(_<0).toList should be(Nil)
  }

  test("map") {
    Stream(1,2,3,4).map(_+1).toList should be(List(2,3,4,5))
  }

  test("append") {
    Stream(1,2,3,4).append(Stream(5,6,7,8)).toList should be(List(1,2,3,4,5,6,7,8))
  }

  test("filter") {
    Stream(1,2,3,4).filter(_>2).toList should be(List(3,4))
  }

  test("flatMap") {
    Stream(1,2,3,4).flatMap(a=>Stream(a+1)).toList  should be(List(2,3,4,5))
  }

}
