package functionalproginscala.ch07purelyfunctionalparallelism

/**
 * Created by sameer on 07/01/15.
 */
object ParallelComputation {

  def sumsRecursively(ints: IndexedSeq[Int]): Int = ints match {
    case sequenceOfSizeUptoOne if (ints.size <= 1) => sequenceOfSizeUptoOne.headOption getOrElse 0
    case _ => {
      val (left, right) = ints.splitAt(ints.size / 2)
      sumsRecursively(left) + sumsRecursively(right)
    }
  }

  def sumsParallely(ints: IndexedSeq[Int]): Par[Int] = ints match {
    case sequenceOfSizeUptoOne if (ints.size <= 1) => Par.unit(sequenceOfSizeUptoOne.headOption getOrElse 0)
    case _ => {
      val (left, right) = ints.splitAt(ints.size / 2)
      Par.map2(sumsParallely(left), sumsParallely(right))(_ + _)
    }
  }

}


class Par[A] {

  def unit[A](a: => A): Par[A] = ???

  def get[A](a: Par[A]): A = ???

  def map2[A,B,C](par1: Par[A], par2: Par[B])(f: (A, B) => C) = ???

  /**
   * marks a computation for concurrent evaluation. The evaluation won’t
   * actually occur until forced by run.
   * @param a
   * @tparam A
   * @return
   */
  def fork[A](a: => Par[A]): Par[A] = ???

  def lazyUnit[A](a: => A): Par[A] = fork(unit(a))

  /**
   * Fully evaluates a given Par, spawning
   * parallel computations as requested by
   * fork and extracting the resulting value
   * @param a
   * @tparam A
   * @return
   */
  def run[A](a: Par[A]): A = ???
}

object Par extends Par[Int] {


}