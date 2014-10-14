package functionalproginscala.ch04errorhandling

import org.scalatest.{Matchers, FunSuite}

class EitherTest extends FunSuite with Matchers {

  import functionalproginscala.ch04errorhandling.Either._

  test("mean") {
    mean(Vector(1.0, 2.0, 3.0, 4.0, 5.0)) should be(Right(3.0))
    mean(Vector()) should be(Left("Empty sequence"))
  }

  test("map") {
    Right("hello").map(_.toUpperCase) should be(Right("HELLO"))
    Left("error").map(x=>x) should be(Left("error"))
  }

  test("flatMap") {
    Right("hello").flatMap(_=>Right("HELLO")) should be(Right("HELLO"))
    Left("error").flatMap(_=>Right("hello")) should be(Left("error"))
  }

  test("orElse") {
    Left("error").orElse(Right("hello")) should be(Right("hello"))
    Right("hello").orElse(Right("HELLO")) should be(Right("hello"))

  }

  test("map2") {
    Right("hello").map2_using_flatMap(Right(" world"))((a,b)=>a+b) should be(Right("hello world"))
    Right("hello").map2_using_for(Right(" world"))((a,b)=>a+b) should be(Right("hello world"))
  }


}
