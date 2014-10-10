import scala.annotation.tailrec

object Lists {

  sealed trait List[+A]

  case object Nil extends List[Nothing]

  case class Cons[+A](head: A, tail: List[A]) extends List[A]

  object FuncList {
    def apply[A](as: A*): List[A] =
      if (as.isEmpty) Nil
      else Cons(as.head, apply(as.tail: _*))

    def sum(ints: List[Int]): Int = ints match {
      case Nil => 0
      case Cons(x, xs) => x + sum(xs)
    }

    def product(ds: List[Double]): Double = ds match {
      case Nil => 1.0
      case Cons(0.0, _) => 0.0
      case Cons(x, xs) => x * product(xs)
    }

    def length[A](list: List[A]): Int = {
      def loop[A](l: List[A], acc: Int): Int = l match {
        case Nil => acc
        case Cons(x, xs) => loop(xs, acc + 1)
      }
      loop(list, 0)
    }


    /**
     * 3.2
     * Implement the function tail for removing the first element of a List. Note that the
     * function takes constant time
     */
    def tail[A](list: List[A]): List[A] = list match {
      case Nil => Nil
      case Cons(x, xs) => xs
    }

    /**
     * 3.3
     * Replace the first element of a List with a different value
     */
    def setHead[A](y: A, list: List[A]) = list match {
      case Nil => Nil
      case Cons(x, xs) => Cons(y, xs)
    }

    /**
     * 3.4
     * Generalize tail to the function drop, which removes the first n elements from a list.
     * Note that this function takes time proportional only to the number of elements being
     * dropped—we don’t need to make a copy of the entire List.
     */
    def drop[A](l: List[A], n: Int): List[A] = l match {
      case _ if (n < 1) => l
      case Cons(x, xs) => drop(xs, n - 1)
    }

    /**
     * 3.5
     * Implement dropWhile, which removes elements from the List prefix as long as they
     * match a predicate.
     */
    def dropWhile[A](l: List[A], f: A => Boolean): List[A] = l match {
      case Nil => Nil
      case Cons(x, xs) => if (f(x)) dropWhile(xs, f) else Cons(x, dropWhile(xs, f))
    }

    /**
     * 3.6
     * returns a List consisting of all but the last element of a List. So, given List(1,2,3,4), init will
     * return List(1,2,3). Why can’t this function be implemented in constant time like tail?
     */
    def init[A](l: List[A]): List[A] = {

      def reverse(l: List[A], acc: List[A]): List[A] = l match {
        case Nil => acc
        case Cons(x, xs) => reverse(xs, Cons(x, acc))
      }

      reverse(drop(reverse(l, Nil), 1), Nil)
    }

    def foldRight[A, B](as: List[A], b: B)(f: (A, B) => B): B = {

      @tailrec
      def loop(list: List[A], acc: B): B = list match {
        case Nil => acc
        case Cons(x, xs) => loop(xs, f(x, acc))
      }

      loop(as, b)
    }


    /**
     * 3.10
     * general list-recursion function, foldLeft, that is tail-recursive
     */
    @tailrec
    def foldLeft[A, B](as: List[A], b: B)(f: (B, A) => B): B = as match {
      case Nil => b
      case Cons(x, xs) => foldLeft(xs, f(b, x))(f)
    }


    /**
     * 3.9 Compute the length of a list using foldRight
     * @param as
     * @tparam A
     * @return
     */
    def len[A](as: List[A]): Int = foldRight(as, 0)((x, y) => y + 1)

    def append[A](a1: List[A], a2: List[A]): List[A] = {
      foldLeft(a1, a2)((tail, y) => Cons(y, tail))
    }

    /**
     * 3.18
     */
    def map[A, B](as: List[A])(f: A => B): List[B] = as match {
      case Nil => Nil
      case Cons(x, xs) => Cons(f(x), map(xs)(f))
    }

    /**
     * 3.19
     *
     */
    def filter[A](as: List[A])(f: A => Boolean): List[A] = {
      flatMap(as)(x => if (f(x)) FuncList(x) else Nil)
    }

    /**
     * 3.20
     */
    def flatMap[A, B](as: List[A])(f: A => List[B]): List[B] = as match {
      case Nil => Nil
      case Cons(x, xs) => append(f(x), flatMap(xs)(f))
    }

    /*def addLists[Int](as: List[Int], bs: List[Int]): List[Int] = as match {
      case Nil => Nil
      case Cons(x , xs) => bs match {
        case Nil => Nil
        case Cons(y, ys) => Cons(x+y , addLists(xs,ys))
      }
    }*/

    /**
     * 3.22
     */
    def addLists[A: Numeric](as: List[A], bs: List[A]): List[A] = as match {
      case Nil => Nil
      case Cons(x, xs) => bs match {
        case Nil => Nil
        case Cons(y, ys) => Cons(implicitly[Numeric[A]].plus(x, y), addLists(xs, ys))
      }
    }

    /**
     * 3.23
     * Generalize the function you just wrote in 3.22 that it’s not specific to integers or addition.
     * Name your generalized function zipWith.
     */
    def zipWith[A](as: List[A], bs: List[A])(f: (A, A) => A): List[A] = as match {
      case Nil => Nil
      case Cons(x: A, xs) => bs match {
        case Nil => Nil
        case Cons(y: A, ys) => Cons(f(x, y), zipWith(xs, ys)(f))
      }
    }

    /**
     * 3.24
     * implement hasSubsequence for checking whether a List contains another List as a subsequence.
     */
    def hasSubsequence[A](sup: List[A], sub: List[A]): Boolean = {

      def startsWith(sup: List[A], sub: List[A]): List[A] = (sup, sub) match {
        case (Cons(x, xs), Cons(y, ys)) if (x == y) => sup
        case (Cons(x, xs), Cons(y, ys)) if (x == y) => startsWith(xs, sub)
        case (Nil, _) => Nil
      }

      def loop(sup: List[A], sub: List[A]): Boolean = (sup, sub) match {
        case (Cons(x, xs), Cons(y, ys)) if (x == y) => loop(xs, ys)
        case (Nil, Cons(y, ys)) => false
        case (Cons(x, xs), Nil) => true
        case (_, _) => false
      }

      val fromStart = startsWith(sup, sub)
      loop(fromStart, sub)

    }

  }

  import FuncList._

  /**
   * 3.1
   */
  val funcList = FuncList(1, 2, 3, 4)

  val x = funcList match {
    case Cons(x, Cons(2, Cons(4, _))) => x
    case Nil => 42
    case Cons(x, Cons(y, Cons(3, Cons(4, _)))) => x + y
    case Cons(h, t) => h + sum(t)
    case _ => 101
  }
  tail(funcList)
  tail(Nil)
  setHead(5, funcList)
  dropWhile(funcList, (x: Int) => x % 2 == 0)
  init(funcList)
  foldRight(funcList, 0)((x, y) => x + y)
  foldRight(funcList, "")((x, y) => x + y)
  //3.8 would reverse list
  foldRight(funcList, Nil: List[Int])(Cons(_, _))
  //3.9 Compute the length of a list using foldRight
  len(Nil)
  len(funcList)
  //3.10
  foldLeft(funcList, "")((x, y) => x + y)
  //3.11 Write sum, product, and a function to compute the length of a list using foldLeft.
  //sum using fold left
  foldLeft(funcList, 0)(_ + _)
  //product using fold left
  foldLeft(funcList, 1)(_ * _)
  foldLeft(funcList, 0)((x, y) => x + 1)
  append(funcList, FuncList(5, 6, 7, 8))
  //3.16 transforms a list of integers by adding 1 to each element.
  map(FuncList(1, 2, 3, 4))(x => x + 1)
  //3.17 turns each value in a List[Double] into a String
  List(1.1, 2.2).map(_.toString)
  filter(funcList)(_ % 2 == 0)
  flatMap(funcList)(x => FuncList(x.toString))
  //that accepts two lists and constructs a new list by adding corresponding elements.
  addLists(funcList, FuncList(5, 6, 7, 8))
  zipWith(funcList, FuncList(5, 6, 7, 8))(_ + _)
  hasSubsequence(funcList, FuncList(1, 3))
  hasSubsequence(funcList, FuncList(1, 2))

}