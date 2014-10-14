package functionalproginscala.ch04errorhandling

import org.scalatest.{Matchers, FunSuite}

class OptionTest extends FunSuite with Matchers{

  import Option._

  test("map in option") {
    Some(5).map(x=>x+1) should be(Some(6))
  }

  test("flatmap in option") {
    Some(5).flatMap(x=>Some(x+1)) should be(Some(6))
    Some(5).flatMap(x=>None) should be(None)
  }

  test("getorelse in option") {
    Some(5).getOrElse(6) should be(5)
    None.getOrElse(6) should be(6)
  }

  test("orElse") {
    None orElse Some(5) should be(Some(5))
    Some(1) orElse Some(5) should be(Some(1))
  }

  test("filter") {
    Some(4) filter(_%2==0) should be(Some(4))
    Some(5) filter(_%2==0) should be(None)
  }

  test("map2") {
    map2(Some(4), None)(_+_) should be(None)
    map2(None, Some(4))((x:Int, y:Int)=>x+y) should be(None)
    map2(Some(4), Some(5))(_+_) should be(Some(9))
  }

  test("sequence") {
    sequence(List(Some(3), Some(4))) should be(Some(List(3,4)))
    sequence(List(Some(3), None)) should be(None)
  }

  test("traverse") {
    traverse[String,Int](List("3","4"))(x=>Some(x.toInt)) should be(Some(List(3,4)))
    traverse2[String,Int](List("3","4"))(x=>Some(x.toInt)) should be(Some(List(3,4)))
  }

}
