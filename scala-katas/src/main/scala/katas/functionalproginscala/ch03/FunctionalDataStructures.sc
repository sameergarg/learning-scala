import scala.annotation.tailrec

object FunctionalDataStructures {

  sealed trait FuncList[+A]

  case object Nil extends FuncList[Nothing]

  case class Cons[+A](head: A, tail: FuncList[A]) extends FuncList[A]

  object FuncList {
    def apply[A](as: A*): FuncList[A] =
      if (as.isEmpty) Nil
      else Cons(as.head, apply(as.tail: _*))

    def sum(ints: FuncList[Int]): Int = ints match {
      case Nil => 0
      case Cons(x, xs) => x + sum(xs)
    }

    def product(ds: FuncList[Double]): Double = ds match {
      case Nil => 1.0
      case Cons(0.0, _) => 0.0
      case Cons(x, xs) => x * product(xs)
    }

    def length[A](list: FuncList[A]): Int = {
      def loop[A](l: FuncList[A], acc: Int): Int = l match {
        case Nil => acc
        case Cons(x, xs) => loop(xs, acc + 1)
      }
      loop(list, 0)
    }

    /**
     * 3.9 Compute the length of a list using foldRight
     * @param as
     * @tparam A
     * @return
     */
    def len[A](as: FuncList[A]): Int = {
      foldRight(as,0)((x,y) => y+1)
    }


    /**
     * 3.2
     * Implement the function tail for removing the first element of a List. Note that the
     * function takes constant time
     */
    def tail[A](list: FuncList[A]): FuncList[A] = list match {
      case Nil => Nil
      case Cons(x, xs) => xs
    }

    /**
     * 3.3
     * Replace the first element of a List with a different value
     */
    def setHead[A](y: A, list: FuncList[A]) = list match {
      case Nil => Nil
      case Cons(x, xs) => Cons(y, xs)
    }

    /**
     * 3.4
     * Generalize tail to the function drop, which removes the first n elements from a list.
     * Note that this function takes time proportional only to the number of elements being
     * dropped—we don’t need to make a copy of the entire List.
     */
    def drop[A](l: FuncList[A], n: Int): FuncList[A] = n match {
      case n if (n < 1) => l
      case _ => tail(l)
    }

    /**
     * 3.5
     * Implement dropWhile, which removes elements from the List prefix as long as they
     * match a predicate.
     */
    def dropWhile[A](l: FuncList[A], f: A => Boolean): FuncList[A] = l match {
      case Nil => Nil
      case Cons(x, xs) => if (f(x)) dropWhile(xs, f) else Cons(x, dropWhile(xs, f))
    }

    /**
     * 3.6
     * returns a List consisting of all but the last element of a List. So, given List(1,2,3,4), init will
     * return List(1,2,3). Why can’t this function be implemented in constant time like tail?
     */
    def init[A](l: FuncList[A]): FuncList[A] = {

      def reverse(l: FuncList[A], acc: FuncList[A]): FuncList[A] = l match {
        case Nil => acc
        case Cons(x, xs) => reverse(xs, Cons(x, acc))
      }

      reverse(drop(reverse(l, Nil), 1), Nil)
    }

    def foldRight[A, B](as: FuncList[A], b: B)(f: (A, B) => B): B = {

      @tailrec
      def loop(list: FuncList[A], acc: B) : B = list match {
        case Nil => acc
        case Cons(x, xs) => loop(xs, f(x,acc))
      }

      loop(as, b)
    }

    /**
     * 3.10
     * general list-recursion function, foldLeft, that is tail-recursive
     */
    def foldLeft[A,B](as: FuncList[A], b: B)(f: (B, A) => B): B = as match {
      case Nil => b
      case Cons(x, xs) => foldLeft(xs, f(b,x))(f)
    }
  }

  import FuncList._

  /**
   * 3.1
   */
  val x = FuncList(1, 2, 3, 4) match {
    case Cons(x, Cons(2, Cons(4, _))) => x
    case Nil => 42
    case Cons(x, Cons(y, Cons(3, Cons(4, _)))) => x + y
    case Cons(h, t) => h + sum(t)
    case _ => 101

  }
  tail(FuncList(1, 2, 3, 4))
  tail(Nil)
  setHead(5, FuncList(1, 2, 3, 4))
  dropWhile(FuncList(1, 2, 3, 4), (x: Int) => x % 2 == 0)
  init(FuncList(1, 2, 3, 4))

  foldRight(FuncList(1, 2, 3, 4), 0)((x,y)=>x+y)

  foldRight(FuncList(1, 2, 3, 4), "")((x,y)=>x+y)
  //3.8 would reverse list
  foldRight(FuncList(1, 2, 3, 4), Nil: FuncList[Int])(Cons(_,_))
  //3.9 Compute the length of a list using foldRight
  len(Nil)
  len(FuncList(1, 2, 3, 4))

}