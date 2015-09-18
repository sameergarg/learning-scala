package katas.futures

import org.scalatest.{Matchers, WordSpec}

import scala.concurrent.{Future, Await}
import scala.concurrent.ExecutionContext.Implicits.global

class FutureComputationsSpec extends WordSpec with Matchers {

  import katas.futures.FutureComputations._
  import scala.concurrent.duration._


  "fibs" should {
    "have 0 as its first number" in {
      fibs.head shouldBe 0
    }

    "have 1 as its second number" in {
      fibs.drop(1).take(1).head shouldBe 1
    }

    "have 1 as its third number" in {
      fibs.drop(2).take(1).head shouldBe 1
    }

    "have 2 as its fourth number" in {
      fibs.drop(3).take(1).head shouldBe 2
    }
  }

  "future fibs" should {
    "have 0 as its first number" in {
      Await.result(futureFib, 1 seconds).head shouldBe 0
    }

    "have 1 as its second number" in {
      Await.result(futureFib, 1 seconds).drop(1).take(1).head shouldBe 1
    }

    "have 1 as its third number" in {
      Await.result(futureFib, 1 seconds).drop(2).take(1).head shouldBe 1
    }

    "have 2 as its fourth number" in {
      Await.result(futureFib, 1 seconds).drop(3).take(1).head shouldBe 2
    }
  }

  "factorize" should {
    "return all factor of 1" in {
      Await.result(factorize(1), 1 seconds) shouldBe (1,Seq(1))
    }

    "return all factor of 2" in {
      Await.result(factorize(2), 1 seconds) shouldBe (2,Seq(1))
    }

    "return all factor of 10" in {
      Await.result(factorize(10), 1 seconds) shouldBe (10,Seq(1,2))
    }
  }

  "factorize fibs" should {
    "return all factor of 10th number in fibonacci sequence" in {
      val result: (Int, Seq[Int]) = Await.result(Future(fibs.drop(28).head).flatMap(factorize(_)), 5 seconds)
      result shouldBe (317811,Seq(1, 3, 13, 29, 39, 87, 281, 377))
    }
  }
}
