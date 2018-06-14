package cats

import cats.data.{Validated, ValidatedNel}
import cats.implicits._
import org.scalatest.{Matchers, WordSpec}

class TraverseSpec extends WordSpec with Matchers {

  def parseIntEither(s: String): Either[NumberFormatException, Int] =
    Either.catchOnly[NumberFormatException](s.toInt)

  def parseIntValidated(s: String): ValidatedNel[NumberFormatException, Int] =
    Validated.catchOnly[NumberFormatException](s.toInt).toValidatedNel

  "Traverse" should {
    "traverse either" in {
      List("1", "2", "3").traverse(parseIntEither) should be(Right(List(1,2,3)))
      List("1", "abc", "3").traverse(parseIntEither).isLeft should be(true)
    }

    "traverse validated" in {
      List("1", "2", "3").traverse(parseIntValidated).isValid should be(true)
    }

    "sequencing" in {
      List(Option(1), Option(2), Option(3)).traverse(identity(_)) should be(Some(List(1,2,3)))
      List(Option(1), None, Option(3)).traverse(identity(_)) should be(None)
    }

    "sequencing syntax" in {
      List(Option(1), Option(2), Option(3)).sequence should be(Some(List(1,2,3)))
      List(Option(1), None, Option(3)).sequence should be(None)
    }

    "sequence_" in {
      List(Option(1), Option(2), Option(3)).sequence_ should be(Some(()))
      List(Option(1), None, Option(3)).sequence_ should be(None)
    }

  }

}
