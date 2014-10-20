package functionalproginscala.ch05laziness

import functionalproginscala.ch05laziness.Stream.{cons, empty}


trait Stream[+A] {

  import functionalproginscala.ch05laziness.Stream._

  def headOption: Option[A] = this match {
    case Empty => None
    case Cons(h, t) => Some(h())
  }

  def headOption_using_foldRight: Option[A] =
    foldRight(None: Option[A])((a, b) => Some(a))

  /**
   * 5.1 convert a Stream to a List which will force its evaluation and let you look at it in the REPL.
   * @return
   */
  def toList: List[A] = this match {
    case Empty => Nil
    case Cons(h, t) => headOption.get :: t().toList
  }

  /**
   * 5.2
   * Return the first n elements of a Stream
   */
  def take(n: Int): Stream[A] = {
    if (n > 0) this match {
      case Empty => Empty
      case Cons(h, t) => Cons[A](h, () => t().take(n - 1))
    } else {
      Empty
    }
  }

  /**
   * 5.3
   * return all starting elements of a Stream that match the given predicate
   */
  def takeWhile(p: A => Boolean): Stream[A] = this match {
    case Empty => Empty
    case Cons(h: A, t) => if (p(h())) Cons[A](h, () => t().takeWhile(p)) else (t().takeWhile(p))
  }

  def foldRight[B](b: => B)(f: (A, => B) => B): B = this match {
    case Empty => b
    case Cons(h, t) => f(h(), t().foldRight(b)(f))
  }

  def exists(p: A => Boolean): Boolean = this.foldRight(false)(p(_) || _)

  /**
   * 5.4
   * checks that all elements in the Stream match a given predicate.
   * Your implementation should terminate the traversal as soon as it encounters a
   * nonmatching value
   */
  def forAll(p: A => Boolean): Boolean =
    foldRight(true)(p(_) && _)

  /**
   * 5.5
   * Use foldRight to implement takeWhile.
   */
  def takeWhile_using_foldRight(p: A => Boolean): Stream[A] =
    foldRight(empty[A])((a, b) =>
      if (p(a))
        cons(a, b)
      else empty[A])

  /**
   * 5.7
   * Implement map, filter, append, and flatMap using foldRight. The append method
   * should be non-strict in its argument.
   */
  def map[B](f: A => B): Stream[B] =
    foldRight(Empty: Stream[B])((a, b) => cons(f(a), b))

  def append[B >: A](b: => Stream[B]) =
    foldRight(b)((a, bs) => cons(a, bs))

  def filter(p: A => Boolean): Stream[A] = {
    foldRight(Empty: Stream[A])((a, b) =>
      if (p(a))
        cons(a, b)
      else
        b)
  }

  def flatMap[B](f: A => Stream[B]): Stream[B] = {
    foldRight(empty[B])((a, b) => f(a) append b)
  }

  /**
   * 5.13
   * Use unfold to implement map, take, takeWhile, zipWith (as in chapter 3), and zipAll.
   */
  def map_unfold[B](f: A => B): Stream[B] = {
    unfold(this) {
      case Cons(h, t) => Some(f(h()), t())
      case Empty => None
    }
  }

  def take_unfold(n: Int): Stream[A] =
    unfold(this,n) {
      case(Cons(h,t),n) if(n>1) => Some(h(),(t(),n-1))
      case(Cons(h,t),n) if(n==1) => Some(h(),(empty, n-1))
      case _ => None
    }

  def takWhile_unfold(p: A => Boolean): Stream[A] =
    unfold(this) {
      case Cons(h, t) if p(h()) => Some(h(), t())
      case _ => None
    }

}

object Empty extends Stream[Nothing]

case class Cons[+A](head: () => A, tail: () => Stream[A]) extends Stream[A]

object Stream {

  def empty[A](): Stream[A] = {
    Empty
  }

  def cons[A](head: => A, tail: => Stream[A]): Stream[A] = {

    lazy val h = head

    lazy val t = tail

    Cons(() => h, () => t)
  }

  /**
   * 5.8
   * Generalize ones slightly to the function constant, which returns an infinite Stream of a given value.
   */
  def constant[A](c: A): Stream[A] =
    cons(c, constant(c))

  /**
   * 5.9
   * generates an infinite stream of integers, starting from n, then n + 1, n + 2, and so on.
   */
  def from(n: Int): Stream[Int] =
    cons(n, from(n + 1))

  /**
   * 5.10
   * generates the infinite stream of Fibonacci numbers: 0, 1, 1, 2, 3, 5, 8,
   */
  val fibs = {
    def loop(secondLast: Int, last: Int): Stream[Int] = {
      cons(secondLast, loop(last, secondLast + last))
    }
    loop(0, 1)
  }

  /**
   * 5.11
   * general stream-building function called unfold. It takes an initial state,
   * and a function for producing both the next state and the next value in the generated
   */
  def unfold[A, S](z: S)(f: S => Option[(A, S)]): Stream[A] = f(z) match {
    case None => empty[A]
    case Some((a, s)) => cons(a, unfold(s)(f))

  }

  /**
   * 5.12
   * Write fibs, from, constant, and ones in terms of unfold
   */
  val fibs_unfold = {
    unfold((0, 1)) { case (a, b) => Some(a, (b, (a + b)))}
  }

  def constant_unfold[A](c: A): Stream[A] = {
    unfold(c)(const => Some((const, const)))
  }

  def from_unfold(n: Int): Stream[Int] = {
    unfold(n)(number => Some((number, number + 1)))
  }


  def apply[A](as: A*): Stream[A] =
    if (as.isEmpty) empty[A] else cons(as.head, apply(as.tail: _*))

}
