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
      def loop(list: FuncList[A], acc: B): B = list match {
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
    def foldLeft[A, B](as: FuncList[A], b: B)(f: (B, A) => B): B = as match {
      case Nil => b
      case Cons(x, xs) => foldLeft(xs, f(b, x))(f)
    }


    /**
     * 3.9 Compute the length of a list using foldRight
     * @param as
     * @tparam A
     * @return
     */
    def len[A](as: FuncList[A]): Int = foldRight(as, 0)((x, y) => y + 1)

    def append[A](a1: FuncList[A], a2: FuncList[A]): FuncList[A] = {
      foldLeft(a1, a2)((tail, y) => Cons(y, tail))
    }

    /**
     * 3.18
     */
    def map[A,B](as: FuncList[A])(f: A => B): FuncList[B] = as match {
      case Nil => Nil
      case Cons(x, xs) => Cons( f(x), map(xs)(f))
    }

    /**
     * 3.19
     *
     */
    def filter[A](as: FuncList[A])(f: A => Boolean): FuncList[A] =  {
      flatMap(as)(x=>if(f(x)) FuncList(x) else Nil)
    }

    /**
     * 3.20
     */
    def flatMap[A,B](as: FuncList[A])(f: A => FuncList[B]): FuncList[B] = as match {
      case Nil => Nil
      case Cons(x, xs) => append(f(x), flatMap(xs)(f))
    }

    /*def addLists[Int](as: FuncList[Int], bs: FuncList[Int]): FuncList[Int] = as match {
      case Nil => Nil
      case Cons(x , xs) => bs match {
        case Nil => Nil
        case Cons(y, ys) => Cons(x+y , addLists(xs,ys))
      }
    }*/

    /**
     * 3.22
     */
    def addLists[A : Numeric](as: FuncList[A], bs: FuncList[A]): FuncList[A] = as match {
      case Nil => Nil
      case Cons(x , xs) => bs match {
        case Nil => Nil
        case Cons(y, ys) => Cons(implicitly[Numeric[A]].plus(x, y), addLists(xs,ys))
      }
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
  foldRight(funcList, Nil: FuncList[Int])(Cons(_, _))
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
  append(funcList, FuncList(5,6,7,8))

  //3.16 transforms a list of integers by adding 1 to each element.
  map(FuncList(1,2,3,4))(x=>x+1)

  //3.17 turns each value in a List[Double] into a String
  List(1.1,2.2).map(_.toString)

  filter(funcList)(_%2==0)
  flatMap(funcList)(x=>FuncList(x.toString))

  //that accepts two lists and constructs a new list by adding corresponding elements.

  addLists(funcList, FuncList(5,6,7,8))

}