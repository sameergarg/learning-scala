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
      def uncurry[A, B, C](f: A => B => C): (A, B) => C = (a, b) => f(a)(b)

      def f(a: Int, b: Int) = a + b

      def g(a: Int)(b: Int) = a + b

      uncurry(g)(1, 1) shouldBe f(1, 1)
      uncurry(g)(1, 1) shouldBe g(1)(1)
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

  "Functional data structures" should {

    "3.1" in {
      val x = List(1, 2, 3, 4, 5) match {
        case ::(x, ::(2, ::(4, _))) => x
        case Nil => 42
        case ::(x, ::(y, ::(3, ::(4, _)))) => x + y
        //case ::(h, t) => h + sum(t)
        case _ => 101
      }
      x shouldBe 3
    }

    "3.2  tail" in {
      def tail[A](l: List[A]): List[A] =
        l match {
          case Nil => sys.error("tail of empty list")
          case ::(_, t) => t
        }

      tail(List(1, 2, 3)) shouldBe List(2, 3)

      tail(List(1)) shouldBe Nil
    }

    "3.3 set head" in {
      def setHead[A](l: List[A], h: A): List[A] = l match {
        case Nil => sys.error("setHead on empty list")
        case ::(_, t) => ::(h, t)
      }

      setHead(List(1, 2, 3), 3) shouldBe List(3, 2, 3)

      setHead(List("a", "b"), "c") shouldBe List("c", "b")

    }

    "3.4 take" in {
      def drop[A](l: List[A], n: Int): List[A] =
        if (n <= 0) l
        else l match {
          case Nil => Nil
          case h :: t => drop(t, n - 1)
        }

      drop(List(1, 2, 3), 1) shouldBe List(2, 3)

      drop(List(1, 2, 3), 0) shouldBe List(1, 2, 3)

      drop(List("a", "b"), 2) shouldBe Nil

      drop(List(1, 2), 3) shouldBe Nil

      drop(Nil, 1) shouldBe Nil

    }

    "3.5 dropWhile" in {
      def dropWhile[A](l: List[A], pred: A => Boolean): List[A] = l match {
        case h :: t if (pred(h)) => dropWhile(t, pred)
        case _ => l
      }

      dropWhile(List(1, 2, 3), (x: Int) => x < 2) shouldBe List(2, 3)

      dropWhile(List(1, 2, 3), (x: Int) => x > 2) shouldBe List(1, 2, 3)

      dropWhile(List(1, 2, 3), (x: Int) => x > 0) shouldBe Nil

      dropWhile(Nil, (x: Int) => x > 0) shouldBe Nil

    }

    "3.6 init" in {
      def init[A](l: List[A]): List[A] = l match {
        case Nil => sys.error("Init of empty list")
        case _ :: Nil => Nil
        case h :: t   => ::(h, init(t))
      }

      init(List(1, 2, 3)) shouldBe List(1, 2)

      init(List(1)) shouldBe Nil

    }

    "3.7 foldRight" in {
      foldRight(Cons(1, Cons(2, Cons(3, Nil))), 0)((x, y) => x + y) shouldBe 6

      + foldRight(Cons(2, Cons(3, Nil)), 0)((x, y) => x + y) shouldBe 6

      +
        + foldRight(Cons(3, Nil), 0)((x, y) => x + y) shouldBe 6

      +
        +
      + foldRight(
        , 0)((x, y) => x + y) shouldBe 6

      +
        +
      +
        shouldBe 6
    }
  }

}
