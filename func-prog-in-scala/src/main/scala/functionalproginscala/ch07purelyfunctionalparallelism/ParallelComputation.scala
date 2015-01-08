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

  def map2(par1: Par[A], par2: Par[A])(f: (A, A) => A) = ???

}

object Par extends Par[Int] {


}