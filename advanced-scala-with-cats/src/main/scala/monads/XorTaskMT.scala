package monads

import cats.Monad
import cats.data._
import monix.eval.Task
import monix.execution.Scheduler.Implicits.global

import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.util.Random


object XorTaskMT extends App {
  implicit val taskMonad = new Monad[Task] {
    override def flatMap[A, B](fa: Task[A])(a: (A) => Task[B]) = fa.flatMap(a)

    override def tailRecM[A, B](a: A)(f: (A) => Task[Either[A, B]]): Task[B] = tailRecM(a)(f)

    override def pure[A](a: A): Task[A] = Task.now(a)
  }


  val generateNum: Task[Either[Throwable, String]] = Task {
    Random.nextInt() % 50 match {
      case i if (i % 2 == 0) => Right("Pass")
      case i if (i % 2 != 0) => Left(new Exception("I am odd"))
    }
  }

  val concat: EitherT[Task, Throwable, String] = for {
    n1 <- EitherT(generateNum)
    n2 <- EitherT(generateNum)
  } yield (n1 + n2)

  println(Await.result(concat.value.runAsync, Duration.Inf))
}
