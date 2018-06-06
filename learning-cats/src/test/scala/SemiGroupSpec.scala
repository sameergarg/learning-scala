import org.scalatest.{Matchers, WordSpec}
import cats.implicits._
import cats.kernel.Semigroup

class SemiGroupSpec extends WordSpec with Matchers {

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

}
