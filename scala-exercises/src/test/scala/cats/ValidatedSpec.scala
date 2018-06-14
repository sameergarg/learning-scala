package cats

import cats.data.Validated.{Invalid, Valid}
import cats.data._
import cats.implicits._
import org.scalatest.{Matchers, WordSpec}

/**
  * https://www.scala-exercises.org/cats/validated
  */
class ValidatedSpec extends WordSpec with Matchers {

  case class ConnectionParams(url: String, port: Int)

  trait Read[A] {
    def read(s: String): Option[A]
  }

  object Read {
    def apply[A](implicit A: Read[A]): Read[A] = A

    implicit val stringRead: Read[String] =
      (s: String) => Some(s)

    implicit val intRead: Read[Int] =
      (s: String) => if (s.matches("-?[0-9]+")) Some(s.toInt)
      else None
  }

  sealed abstract class ConfigError
  final case class MissingConfig(field: String) extends ConfigError
  final case class ParseError(field: String) extends ConfigError


  case class Config(map: Map[String, String]) {
    def parse[A: Read](key: String): Validated[ConfigError, A] =
      map.get(key) match {
        case None => Invalid(MissingConfig(key))
        case Some(value) =>
          Read[A].read(value) match {
            case None => Invalid(ParseError(key))
            case Some(a) => Valid(a)
          }
      }
  }

  def parallelValidate[E: Semigroup, A, B, C](v1: Validated[E, A], v2: Validated[E, B])(f: (A, B) => C): Validated[E, C] =
    (v1, v2) match {
      case (Valid(a), Valid(b)) => Valid(f(a, b))
      case (Valid(_), i @ Invalid(_)) => i
      case (i @ Invalid(_), Valid(_)) => i
      case (Invalid(e1), Invalid(e2)) => Invalid(Semigroup[E].combine(e1, e2))
    }

  implicit val nelSemigroup: Semigroup[NonEmptyList[ConfigError]] =
    SemigroupK[NonEmptyList].algebra[ConfigError]

  implicit val readString: Read[String] = Read.stringRead
  implicit val readInt: Read[Int] = Read.intRead



  "Validated" should {

    "validate no error" in {
      val config = Config(Map(("url", "127.0.0.1"), ("port", "1337")))

      val valid = parallelValidate(
        config.parse[String]("url").toValidatedNel,
        config.parse[Int]("port").toValidatedNel
      )(ConnectionParams.apply)

      valid.isValid should be(true)
      valid.getOrElse(ConnectionParams("", 0)) should be(ConnectionParams("127.0.0.1",1337))
    }

    "validate with error" in  {
      val config = Config(Map(("endpoint", "127.0.0.1"), ("port", "not a number")))

      val invalid = parallelValidate(
        config.parse[String]("url").toValidatedNel,
        config.parse[Int]("port").toValidatedNel
      )(ConnectionParams.apply)

      invalid.isValid should be(false)
      val errors = NonEmptyList(MissingConfig("url"), List(ParseError("port")))
      invalid == Validated.invalid(errors) should be(true)
    }

    "sequential validation using and then" in {
      val config = Config(Map("house_number" → "-42"))

      val houseNumber = config.parse[Int]("house_number").andThen { n =>
        if (n >= 0) Validated.valid(n)
        else Validated.invalid(ParseError("house_number"))
      }

      houseNumber.isValid should be(false)
      val error = ParseError("house_number")
      houseNumber == Validated.invalid(error) should be(true)
    }

    "withEither" in {
      def positive(field: String, i: Int): Either[ConfigError, Int] = {
        if (i >= 0) Either.right(i)
        else Either.left(ParseError(field))
      }

      val config = Config(Map("house_number" → "-42"))

      val houseNumber = config.parse[Int]("house_number").withEither {
        either: Either[ConfigError, Int] ⇒
          either.flatMap { i ⇒
            positive("house_number", i)
          }
      }

      houseNumber.isValid should be(false)
      val error = ParseError("house_number")
      houseNumber == Validated.invalid(error) should be(true)
    }

  }

}
