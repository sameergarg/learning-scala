package cats

import org.scalatest.{Matchers, WordSpec}

class IdentitySpec extends WordSpec with Matchers {

  "Id" should {
    "int id" in {
      val anId: Id[Int] = 42
      anId should be(42)
    }

    "map" in {
      val one: Int = 1
      Functor[Id].map(one)(_ + 1) should be(2)
    }

    "pure" in {
      Applicative[Id].pure(42) should be(42)
    }

    "map and flatmap" in {
      val one: Int = 1
      Monad[Id].map(one)(_ + 1)
      Monad[Id].flatMap(one)(_ + 1)
    }
  }

}
