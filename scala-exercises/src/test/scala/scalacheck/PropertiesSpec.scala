package scalacheck

import org.scalacheck.Prop._
import org.scalacheck.{Gen, Properties}
import org.scalatest.WordSpec
import org.scalatest.prop.Checkers

/**
  * https://www.scala-exercises.org/scalacheck/properties
  */
class PropertiesSpec extends WordSpec with Checkers {

  "Prop" should {
    "forAll list size" in {
      val listSizeProp = forAll{ (l1: List[Int], l2: List[Int]) => l1.size + l2.size == (l1:::l2).size }
      check(listSizeProp)
    }

    "forAll string concat" in {
      val concatProp = forAll{(s1: String, s2: String) => (s1 + s2).endsWith(s2) == true}
      check(concatProp)
    }

    "choose" in {
      val from0To100 = Gen.choose(0, 100)
      val inRangeProp = forAll(from0To100)(n => n >=0 && n <=100)
      check(inRangeProp)
    }

    "implication" in {
      check(
        forAll{ (n: Int) =>
          (n % 2 == 0)  ==> (n % 2 == 0)
        }
      )
    }
  }


  class ZeroSpecification extends Properties("Zero") {

    import org.scalacheck.Prop.forAll

    property("addition property") = forAll { n: Int =>
      (n != 0) ==> (n +
        0 == n)
    }

    property("additive inverse property") = forAll { n: Int =>
      (n != 0) ==> (n + (-n) == 0
        )
    }

    property("multiplication property") = forAll { n: Int =>
      (n != 0) ==> (n * 0 == 0)
    }

  }

  check(all(new ZeroSpecification().properties.map(_._2): _*))

}
