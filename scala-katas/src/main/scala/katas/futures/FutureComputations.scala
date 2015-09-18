package katas.futures

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global


object FutureComputations {

  lazy val fibs: Stream[Int] = 0 #:: 1 #:: fibs.zip(fibs.tail).map(pair => pair._1 + pair._2)

  lazy val futureFib: Future[Stream[Int]] = Future {
    fibs
  }

  def factorize(n: Int): Future[(Int, Seq[Int])] = {
    import Math._

    Future {(n, (1 to floor(sqrt(n)).toInt) filter (n % _ == 0))}

  }



}
