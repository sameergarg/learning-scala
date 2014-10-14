package functionalproginscala.ch04errorhandling


/**
 * Implement all functions inside Option trait
 * @tparam A
 */
//4.1 implement trait methods for Option
trait Option[+A] {

  def map[B](f: A => B): Option[B] = this match {
    case Some(a) => Some(f(a))
    case None => None
  }

  /**
   * returns the result inside the Some case of the Option, or if the Option is None, returns the given default value
   * @param default
   * @tparam B
   * @return
   */
  def getOrElse[B >: A](default: => B): B = this match {
    case Some(a) => a
    case None => default
  }


  def flatMap[B](f: A => Option[B]): Option[B] =
    map(f) getOrElse None

  /**
   * returns the first Option if it’s defined; otherwise, it returns the second Option.
   * @param ob
   * @tparam B
   * @return
   */
  def orElse[B >: A](ob: => Option[B]): Option[B] =
    this map (Some(_)) getOrElse (ob)

  /**
   * Convert Some to None if the value doesn’t satisfy f.
   * @param f
   * @return
   */
  def filter(f: A => Boolean): Option[A] = flatMap(a => if (f(a)) Some(a) else None)

}

case class Some[+A](get: A) extends Option[A]

case object None extends Option[Nothing]

object Option {

  def mean(xs: Seq[Double]): Option[Double] =
    if (xs isEmpty) None else Some(xs.sum / xs.length)

  /**
   * 4.2
   * Implement the variance function in terms of flatMap. If the mean of a sequence is m,
   * the variance is the mean of math.pow(x - m, 2) for each element x in the sequence.
   * See the definition of variance on Wikipedia (http://mng.bz/0Qsr).
   * @param xs
   * @return
   */
  def variance(xs: Seq[Double]): Option[Double] = {
    mean(xs) flatMap (m => mean(xs map (x => math.pow(x - m, 2))))
  }

  /**
   * 4.3
   * Combines two Option values using a binary function.
   * If either Option value is None, then the return value is too.
   */
  /*
    def map2[A,B,C](a: Option[A], b: Option[B])(f: (A, B) => C): Option[C] = (a,b) match {
      case(Some(a), Some(b)) => Some(f(a,b))
      case (_,_) => None
    }
  */
  //using flatmap
  def map2[A, B, C](a: Option[A], b: Option[B])(f: (A, B) => C): Option[C] = {
    a flatMap (aa =>
      b map (bb =>
        f(aa, bb)))
  }

  /**
   * combines a list of Options into one Option containing
   * a list of all the Some values in the original list. If the original list contains None even
   * once, the result of the function should be None; otherwise the result should be Some
   * with a list of all the values.
   */

  def sequence[A](a: List[Option[A]]): Option[List[A]] =
    a.foldRight[Option[List[A]]](Some(Nil))((x, y) => map2(x, y)(_ :: _))

  def traverse[A, B](a: List[A])(f: A => Option[B]): Option[List[B]] = {
    //inefficient as it traverse list twice
    sequence(a.map(aa => f(aa)))
  }

  def traverse2[A, B](a: List[A])(f: A => Option[B]): Option[List[B]] = {
    a.foldRight[Option[List[B]]](Some(Nil))((x, y) => map2(f(x), y)(_ :: _))
  }
}
