package functionalproginscala.ch04errorhandling

/**
 * 4.6
 * Implement versions of map, flatMap, orElse, and map2 on Either that operate on the Right value.
 * @tparam E
 * @tparam A
 */
trait Either[+E, +A] {

  def map[B](f: A => B): Either[E, B] = this match {
    case Right(a) => Right(f(a))
    case Left(e) => Left(e)
  }

  def flatMap[EE >: E, B](f: A => Either[EE, B]): Either[EE, B] = this match {
    case Right(a) => f(a)
    case Left(e) => Left(e)
  }

  def orElse[EE >: E, B >: A](b: => Either[EE, B]): Either[EE, B] = this match {
    case Right(a) => Right(a)
    case Left(e) => b
  }

  def map2_using_flatMap[EE >: E, B, C](b: Either[EE, B])(f: (A, B) => C): Either[EE, C] =
    this flatMap (aa => b.map(f(aa, _)))

  def map2_using_for[EE >: E, B, C](b: Either[EE, B])(f: (A, B) => C): Either[EE, C] =
    for (aa <- this; bb <- b) yield f(aa, bb)
}

case class Left[+E](error: E) extends Either[E, Nothing]

case class Right[+A](success: A) extends Either[Nothing, A]

object Either {

  def mean(xs: IndexedSeq[Double]): Either[String, Double] =
    if (xs.isEmpty) Left("Empty sequence") else Right(xs.sum / xs.length)

  /**
   * 4.7
   * Implement sequence and traverse for Either. These should return the first error
   * that’s encountered, if there is one.
   */
  def sequence[E, A](es: List[Either[E, A]]): Either[E, List[A]] = {
    traverse(es)(x=>x)
  }

  def traverse[E, A, B](as: List[A])(f: A => Either[E, B]): Either[E, List[B]] = {
    as.foldRight[Either[E, List[B]]](Right(Nil))((a,eb) => f(a).map2_using_for(eb)(_::_))
  }

  def traverse_using_matcher[E, A, B](as: List[A])(f: A => Either[E, B]): Either[E, List[B]] = as match {
    case Nil => Right(Nil)
    case h::t => f(h).map2_using_flatMap(traverse_using_matcher(t)(f))(_::_)

  }

}
