package monads

import cats.data.OptionT
import cats.implicits._

import scala.concurrent.{Await, Future}
import scala.util.Random
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration

case class FutureOption[A](underlying: Future[Option[A]]) {

  def map[B](f: A => B): FutureOption[B] =
    FutureOption(underlying.map(mayBeValue => mayBeValue.map(f)))

  def flatMap[B](f: A => FutureOption[B]): FutureOption[B] = FutureOption(
    underlying.flatMap{ maybeA =>
      maybeA match {
        case Some(a) => f(a).underlying
        case None => Future.successful(None)
      }
    }
  )
}

object MonadTransformer extends App {

  private def generateNum: Future[Option[Int]] = Future {
    val i = Math.abs(Random.nextInt())
    Some(i)
    //if(i % 2 == 0) Some(i) else None
  }

  val num1 = generateNum
  val num2 = generateNum
  val num3 = generateNum

  val usingNestedMonad = for {
    mayBe1 <- num1
    mayBe2 <- num2
    mayBe3 <- num3
  } yield for {
    n1 <- mayBe1
    n2 <- mayBe2
    n3 <- mayBe3
  } yield (n1 + n2 + n3)

  val usingMonadTransformer = for {
    n1 <- FutureOption(num1)
    n2 <- FutureOption(num2)
    n3 <- FutureOption(num3)
  } yield (n1 + n2 + n3)

  val usingCats = for {
    n1 <- OptionT(num1)
    n2 <- OptionT(num2)
    n3 <- OptionT(num3)
  } yield (n1 + n2 + n3)

  println(Await.result(usingNestedMonad, Duration.Inf))

  println(Await.result(usingMonadTransformer.underlying, Duration.Inf))

  println(Await.result(usingCats.value, Duration.Inf))


}
