package scala.excersice.cats

import cats.{Foldable, Later, Now}
import org.scalatest.{Matchers, WordSpec}
import cats.implicits._
import cats.kernel.Monoid

class FoldableSpec extends WordSpec with Matchers {

  "Foldable" should {
    "foldleft int" in {
      Foldable[List].foldLeft(List(1, 2, 3), 0)(_ + _) should be(6)
    }

    "foldleft string" in {
      Foldable[List].foldLeft(List("a", "b", "c"), "")(_ + _) should be("abc")
    }

    "foldright" in {
      val lazyResult =
        Foldable[List].foldRight(List(1, 2, 3), Now(0))((x, rest) ⇒ Later(x + rest.value))
      lazyResult.value should be(6)
    }

    "fold string" in {
      Foldable[List].fold(List("a", "b", "c")) should be("abc")
    }

    "fold int" in {
      Foldable[List].fold(List(1, 2, 3)) should be(6)
    }

    "foldmap" in {
      Foldable[List].foldMap(List("a", "b", "c"))(_.length) should be(3)
      Foldable[List].foldMap(List(1, 2, 3))(_.toString) should be("123")
    }

    "foldk" in {
      Foldable[List].foldK(List(List(1, 2), List(3, 4, 5))) should be(List(1,2,3,4,5))
      val maybeString: Option[String] = Foldable[List].foldK(List(None, Option("two"), Option("three")))
      println(Monoid[List[String]].combine(List("hello", "world"), List("goodbye", "moon")))
      println(maybeString)
      //maybeString should be(List(Some("two"), Some("three")))
    }

    "find" in {
      Foldable[List].find(List(1, 2, 3))(_ > 2) should be(Some(3))
      Foldable[List].find(List(1, 2, 3))(_ > 5) should be(None)
    }

    "exists" in {
      Foldable[List].exists(List(1, 2, 3))(_ > 2) should be(true)
      Foldable[List].exists(List(1, 2, 3))(_ > 5) should be(false)
    }

    "for all" in {
      Foldable[List].forall(List(1, 2, 3))(_ <= 3) should be(true)
      Foldable[List].forall(List(1, 2, 3))(_ < 3) should be(false)
    }

    "tolist" in {
      Foldable[List].toList(List(1, 2, 3)) should be(List(1,2,3))
      Foldable[Option].toList(Option(42)) should be(List(42))
      Foldable[Option].toList(None) should be(List.empty)
    }

    "filter_" in {
      Foldable[List].filter_(List(1, 2, 3))(_ < 3) should be(List(1,2))
      Foldable[Option].filter_(Option(42))(_ != 42) should be(List.empty)
    }

    "traverse_" in {
      import cats.implicits._

      def parseInt(s: String): Option[Int] =
        Either.catchOnly[NumberFormatException](s.toInt).toOption

      Foldable[List].traverse_(List("1", "2", "3"))(parseInt) should be(Some(()))
      Foldable[List].traverse_(List("a", "b", "c"))(parseInt) should be(None)
    }

    "compose" in {
      val FoldableListOption = Foldable[List].compose[Option]
      FoldableListOption.fold(List(Option(1), Option(2), Option(3), Option(4))) should be(10)
      FoldableListOption.fold(List(Option("1"), Option("2"), None, Option("3"))) should be("123")
    }

    "miscellaneous" in {
      Foldable[List].isEmpty(List(1, 2, 3)) should be(false)
      Foldable[List].dropWhile_(List(1, 2, 3))(_ < 2) should be(List(2,3))
      Foldable[List].takeWhile_(List(1, 2, 3))(_ < 2) should be(List(1))
    }
  }

}
