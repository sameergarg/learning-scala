package fpinscala

import org.scalatest.{Matchers, WordSpec}

class FPInScalaSpec extends WordSpec with Matchers {

  "Tail recursive function" should {
    "loop" in {
      def fib(n: Int): Int = {
        @annotation.tailrec
        def loop(n: Int, prev: Int, cur: Int): Int =
          if (n <= 0) prev
          else loop(n - 1, cur, prev + cur)

        loop(n, 0, 1)
      }

      fib(5) should be(5)
    }
  }

  "hof" should {
    "sort" in {
      def isSorted[A](as: Array[A], ordering: (A, A) => Boolean): Boolean = {
        @annotation.tailrec
        def go(n: Int): Boolean =
          if (n >= as.length - 1) true
          else if (ordering(as(n), as(n + 1))) false
          else go(n + 1)

        go(0)
      }

      isSorted(Array(1, 3, 5, 7), (x: Int, y: Int) => x > y) shouldBe true

      isSorted(Array(7, 5, 1, 3), (x: Int, y: Int) => x < y) shouldBe false

      isSorted(Array("Scala", "Exercises"), (x: String, y: String) => x.length > y.length) shouldBe true

    }
  }

  "curry" should {
    "uncurry" in {
      def uncurry[A,B,C](f: A => B=> C): (A,B) => C = (a,b) => f(a)(b)

      def f(a: Int, b: Int) = a + b
      def g(a: Int)(b:Int) = a + b

      uncurry(g)(1,1) shouldBe f(1,1)
      uncurry(g)(1,1) shouldBe g(1)(1)
    }
  }

  "compose" should {
    "compose function" in {
      def compose[A, B, C](f: B => C, g: A => B): A => C = g andThen f

      def f(b: Int): Int = b / 2
      def g(a: Int): Int = a + 2

      compose(f, g)(0) == compose(g, f)(0) shouldBe false
      compose(f, g)(2) shouldBe 2

      compose(g, f)(2) shouldBe 3

    }
  }

}
