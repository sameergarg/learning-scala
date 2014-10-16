package functionalproginscala.ch05laziness

import functionalproginscala.ch05laziness.Stream.{cons, empty}


trait Stream[+A] {

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
    foldRight(empty[B])((a, b) => f(a) append b )
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

  def apply[A](as: A*): Stream[A] =
    if (as.isEmpty) empty[A] else cons(as.head, apply(as.tail: _*))

}
