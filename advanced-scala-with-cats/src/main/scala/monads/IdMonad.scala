package monads

import cats.Monad
import cats.syntax.flatMap._
import cats.syntax.functor._

object IdMonad extends App {

  type MyId[A] = A
  implicit val myId = new Monad[MyId] {
    override def pure[A](x: A) = x

    override def flatMap[A, B](fa: MyId[A])(f: (A) => MyId[B]) = f(fa)

    override def tailRecM[A, B](a: A)(f: (A) => MyId[Either[A, B]]): MyId[B] = tailRecM(a)(f)
  }

  val s1: MyId[Int] = 1
  val s2: MyId[Int] = 2

  println(
    for {
      pre <- s1
      post <- s2
    } yield s1 + s2
  )


}
