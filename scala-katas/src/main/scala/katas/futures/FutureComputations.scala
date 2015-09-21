package katas.futures

import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

object FutureComputations extends App {

  lazy val fibs: Stream[Int] = 0 #:: 1 #:: fibs.zip(fibs.tail).map(pair => pair._1 + pair._2)

  lazy val futureFib: Future[Stream[Int]] = Future {
    fibs
  }

  def factorize(n: Int): Future[(Int, Seq[Int])] = {
    import Math._

    Future {
      (n, (1 to floor(sqrt(n)).toInt) filter (n % _ == 0))
    }

  }

  val combiningFutures = {
    def futureSum = Future {
      (1 to 5).foldLeft(0) { (acc, i) =>
        println(s"Future 1 - $i")
        Thread.sleep(100)
        acc + i
      }
    }

    val futureConcat = Future {
      ('A' to 'C').foldLeft("") { (acc, i) =>
        println(s"Future 2 - $i")
        Thread.sleep(10)
        acc+i
      }
    }

    futureSum.flatMap{ sum => futureConcat.map{concat =>(concat,sum)}}

  }

  def transformFuture(a: Any) = {
    Future(a.toString.toInt).transform (
      s => s,
      t => new Exception("Couldn't convert to int: " + t)
    )
  }

  println(Await.result(combiningFutures, 5 seconds))

  println(Await.result(transformFuture(1), 1 second))
  println(Await.result(transformFuture('A'), 1 second))

}
