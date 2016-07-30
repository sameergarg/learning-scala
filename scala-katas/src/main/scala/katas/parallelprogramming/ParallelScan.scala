package katas.parallelprogramming

/**
  * Created by samegarg on 29/07/2016.
  */
object ParallelScan {


  def scanLeftSeq[A](inp: Array[A], a0: A, f: (A,A) => A, out: Array[A]) {
    out(0)= a0
    for(i <- 1 to inp.length)
      out(i) = f(out(i-1), inp(i-1))
  }



}
