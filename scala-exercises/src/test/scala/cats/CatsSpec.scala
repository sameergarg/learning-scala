package cats

import org.scalatest.{Matchers, WordSpec}
import cats.implicits._
import cats.kernel.{Monoid, Semigroup}
import cats.{Applicative, Apply, Functor, Monad}
import org.scalatest.{Matchers, WordSpec}

class CatsSpec extends WordSpec with Matchers {

  "Semigroup" should {
    "combine types" in {
      Semigroup[Int].combine(1, 2) should be(
        3
      )

      Semigroup[List[Int]].combine(List(1, 2, 3), List(4, 5, 6)) should be(
        List(1, 2, 3, 4, 5, 6)
      )

      Semigroup[Option[Int]].combine(Option(1), Option(2)) should be(
        Some(3)
      )
      Semigroup[Option[Int]].combine(Option(1), None) should be(
        Some(1)
      )
    }
  }

  "Monoid" should {
    "have combine and empty for Strings" in {
      Monoid[String].empty should be("")

      Monoid[String].combineAll(List("a", "b", "c")) should be("abc")
      Monoid[String].combineAll(List()) should be("")
    }

    "have combine and empty for Maps" in {
      Monoid[Map[String, Int]].combineAll(List(Map("a" → 1, "b" → 2), Map("a" → 3))) should be(Map("a" -> 4, "b" -> 2))
      Monoid[Map[String, Int]].combineAll(List()) should be(Map())
    }

    "work for tuples" in {
      val l = List(1, 2, 3, 4, 5)
      l.foldMap(i ⇒ (i, i.toString)) should be((15, "12345"))
    }
  }

  "Foldmap" should {
    "fold and map over custom types" in {
      val l = List(1, 2, 3, 4, 5)
      l.foldMap(identity) should be(15)
      l.foldMap(i ⇒ i.toString) should be("12345")
    }
  }

  "Functor" should {
    "map" in {

      def func1Functor[In] = new Functor[(In) => ?] {
        override def map[A, B](fa: In => A)(f: A => B): In => B = fa andThen f
      }

      Functor[Option].map(Option("Hello"))(_.length) should be(Some(5))
    }

    "lift" in {
      val lenOption: Option[String] => Option[Int] = Functor[Option].lift(_.length)

      lenOption(Some("abcd")) shouldBe Some(4)
    }

    "fproduct" in {
      val source = List("Cats", "is", "awesome")
      val product = Functor[List].fproduct(source)(_.length).toMap

      product.get("Cats").getOrElse(0) should be(4)
      product.get("is").getOrElse(0) should be(2)
      product.get("awesome").getOrElse(0) should be(7)
    }

    "compose" in {
      val listOpt = Functor[List] compose Functor[Option]
      listOpt.map(List(Some(1), None, Some(3)))(_ + 1) should be(List(Some(2), None, Some(4)))
    }
  }

  "Apply" should {
    val intToString: Int ⇒ String = _.toString
    val double: Int ⇒ Int = _ * 2
    val addTwo: Int ⇒ Int = _ + 2

    val addArity2 = (a: Int, b: Int) ⇒ a + b
    val addArity3 = (a: Int, b: Int, c: Int) ⇒ a + b + c

    "map" in {

      Apply[Option].map(Some(1))(intToString) should be(Some("1"))
      Apply[Option].map(Some(1))(double) should be(Some(2))
      Apply[Option].map(None)(addTwo) should be(None)
    }

    "compose" in {
      val listOpt = Apply[List] compose Apply[Option]
      val plusOne = (x: Int) ⇒ x + 1
      listOpt.ap(List(Some(plusOne)))(List(Some(1), None, Some(3))) should be(List(Some(2), None, Some(4)))
    }

    "ap" in {
      Apply[Option].ap(Some(intToString))(Some(1)) should be(Some("1"))
      Apply[Option].ap(Some(double))(Some(1)) should be(Some(2))
      Apply[Option].ap(Some(double))(None) should be(None)
      Apply[Option].ap(None)(Some(1)) should be(None)
      Apply[Option].ap(None)(None) should be(None)
    }

    "ap with arity" in {
      Apply[Option].ap2(Some(addArity2))(Some(1), Some(2)) should be(Some(3))
      Apply[Option].ap2(Some(addArity2))(Some(1), None) should be(None)

      Apply[Option].ap3(Some(addArity3))(Some(1), Some(2), Some(3)) should be(Some(6))
    }

    "map with arity" in {
      Apply[Option].map2(Some(1), Some(2))(addArity2) should be(Some(3))
      Apply[Option].map3(Some(1), Some(2), Some(3))(addArity3) should be(Some(6))
    }

    "tuple n" in {
      Apply[Option].tuple2(Some(1), Some(2)) should be(Some((1, 2)))
      Apply[Option].tuple3(Some(1), Some(2), Some(3)) should be(Some(1, 2, 3))
    }

    "syntax" in {
      val option2 = (Option(1), Option(2))
      val option3 = (Option(1), Option(2), Option.empty[Int])

      option2 mapN addArity2 should be(Some(3))

      option3 mapN addArity3 should be(None)

      option2 apWith Some(addArity2) should be(Some(3))
      option3 apWith Some(addArity3) should be(None)

      option2.tupled should be(Some(1, 2))
      option3.tupled should be(None)
    }
  }

  "Applicative" should {
    "pure" in {
      Applicative[Option].pure(1) should be(Some(1))
      Applicative[List].pure(1) should be(List(1))
    }

    "compose" in {
      (Applicative[List] compose Applicative[Option]).pure(1) should be(List(Some(1)))
    }

    "applicative functor and monad" in {
      Monad[Option].pure(1) should be(Some(1))
      Applicative[Option].pure(1) should be(Some(1))
    }
  }

  "Monad" should {
    "flatten" in {
      Option(Option(1)).flatten should be(Some(1))
      Option(None).flatten should be(None)
      List(List(1), List(2, 3)).flatten should be(List(1, 2, 3))
    }

    def optionMonad(implicit optionApp: Applicative[Option]): Monad[Option] = new Monad[Option] {
      override def pure[A](x: A): Option[A] = optionApp.pure(x)

      override def flatMap[A, B](fa: Option[A])(f: A => Option[B]): Option[B] = fa.map(f).flatten

      override def tailRecM[A, B](a: A)(f: A => Option[Either[A, B]]): Option[B] = {
        f(a).flatMap(_.toOption)
      }
    }
  }

  "pure" in {
    Monad[Option].pure(42) should be(Some(42))
  }

  "flatmap" in {
    Monad[List].flatMap(List(1, 2, 3))(x ⇒ List(x, x)) should be(List(1, 1, 2, 2, 3, 3))
  }

  "ifM" in {
    Monad[Option].ifM(Option(true))(Option("truthy"), Option("falsy")) should be(Some("truthy"))
    Monad[List].ifM(List(true, false, true))(List(1, 2), List(3, 4)) should be(List(1, 2, 3, 4, 1, 2))
  }
}
