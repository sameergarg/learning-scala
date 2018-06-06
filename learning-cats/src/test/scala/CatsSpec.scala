import cats.Functor
import org.scalatest.{Matchers, WordSpec}
import cats.implicits._
import cats.kernel.{Monoid, Semigroup}

class CatsSpec extends WordSpec with Matchers {

  "Semigroup" should {
    "combine types" in {
      Semigroup[Int].combine(1, 2) should be(
        3
      )

      Semigroup[List[Int]].combine(List(1, 2, 3), List(4, 5, 6)) should be(
        List(1,2,3,4,5,6)
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
      Monoid[Map[String, Int]].combineAll(List(Map("a" → 1, "b" → 2), Map("a" → 3))) should be(Map("a"->4, "b"->2))
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
    "have map defined" in {

      def func1Functor[In] = new Functor[({type F[A] = (In) => A})#F] {
        override def map[A, B](fa: In => A)(f: A => B): In => B = fa andThen f
      }
    }
  }

}
