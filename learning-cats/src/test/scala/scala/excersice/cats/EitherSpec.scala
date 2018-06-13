package scala.excersice.cats

import cats.Monad
import org.scalatest.{Matchers, WordSpec}
import cats.implicits._
import scala.util.Try

class EitherSpec extends WordSpec with Matchers {

  "Either" should {
    "map" in {
      val right: Either[String, Int] = Right(5)
      right.map(_ + 1) should be(Right(6))

      val left: Either[String, Int] = Left("Something went wrong")
      left.map(_ + 1) should be(left)
    }

    "define as monad" in {
      implicit def eitherMonad[Err]: Monad[Either[Err, ?]] =
        new Monad[Either[Err, ?]] {
          def flatMap[A, B](fa: Either[Err, A])(f: A => Either[Err, B]): Either[Err, B] =
            fa.flatMap(f)

          def pure[A](x: A): Either[Err, A] = Right(x)

          override def tailRecM[A, B](a: A)(f: A => Either[Err, Either[A, B]]): Either[Err, B] = {
            f(a) match {
              case Left(err) => (Left[Err, B](err): Either[Err,B])
              case Right(Left(a1: Err)) => Left[Err, B](a1)
              case Right(Right(b)) => Right(b)
            }
          }
        }
    }

    "flatMap" in {
      val right: Either[String, Int] = Right(5)
      right.flatMap(x ⇒ Right(x + 1)) should be(Right(6))

      val left: Either[String, Int] = Left("Something went wrong")
      left.flatMap(x ⇒ Right(x + 1)) should be(left)
    }

    "exception handling" in {
      object EitherStyle {
        def parse(s: String): Either[NumberFormatException, Int] =
          if (s.matches("-?[0-9]+")) Right(s.toInt)
          else Left(new NumberFormatException(s"${s} is not a valid integer."))

        def reciprocal(i: Int): Either[IllegalArgumentException, Double] =
          if (i == 0) Left(new IllegalArgumentException("Cannot take reciprocal of 0."))
          else Right(1.0 / i)

        def stringify(d: Double): String = d.toString

        def magic(s: String): Either[Exception, String] =
          parse(s).flatMap(reciprocal).map(stringify)
      }
       import EitherStyle._

      parse("Not a number").isRight should be(false)
      parse("2").isRight should be(true)

      magic("0").isRight should be(false)
      magic("1").isRight should be(true)
      magic("Not a number").isRight should be(false)

      val result = magic("2") match {
        case Left(_: NumberFormatException) ⇒ "Not a number!"
        case Left(_: IllegalArgumentException) ⇒ "Can't take reciprocal of 0!"
        case Left(_) ⇒ "Unknown error"
        case Right(result) ⇒ s"Got reciprocal: ${result}"
      }
      result should be("Got reciprocal: 0.5")
    }

    "leftMap and map" in {
      val right: Either[String, Int] = Right(41)
      right.map(_ + 1) should be(Right(42))

      val left: Either[String, Int] = Left("Hello")
      left.map(_ + 1) should be(Left("Hello"))
      left.left.map(_.reverse) should be(Left("olleH"))
    }

    "catchonly" in {
      Either.catchOnly[NumberFormatException]("abc".toInt).isRight should be(false)

      Either.catchNonFatal(1 / 0).isLeft should be(true)
    }
  }

}
