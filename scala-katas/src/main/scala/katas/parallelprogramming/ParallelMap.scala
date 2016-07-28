package katas.parallelprogramming
import common._
/**
  * Created by samegarg on 28/07/2016.
  */
object ParallelMap {

  object ParallelArray {
    val threshold = 4

    def mapSeq[A,B](list: List[A], f: A => B): List[B] = list match {
      case Nil => Nil
      case head:: tail => f(head) :: mapSeq(tail, f)
    }

    def mapASegSeq[A, B](inp: Array[A], left: Int, right: Int, f: A=> B, out: Array[B]) =
      for(i <- left to right-1)
        out(i) = f(inp(i))


    def mapASegPar[A,B](inp: Array[A], left: Int, right: Int, f: A=> B, out: Array[B]): Unit = {
      if (right - left < threshold)
        mapASegSeq(inp, left, right, f, out)
      else {
        val mid = left + (right - left)/2
        parallel(
          mapASegPar(inp, mid, right, f, out),
          mapASegPar(inp, left, mid, f, out)
        )
      }
    }
  }

  object ParallelTree {
    sealed abstract class Tree[A]{val size: Int}

    case class Leaf[A](a: Array[A]) extends Tree[A] {
      override val size: Int = a.size
    }

    case class Node[A](left: Tree[A], right: Tree[A]) extends Tree[A] {
      override val size: Int = left.size + right.size
    }

    def mapTreePar[A: Manifest, B: Manifest](t: Tree[A], f: A => B): Tree[B] = t match {
      case Leaf(a) =>
        val b = new Array[B](a.length)
        for(i <- 0 to a.length)
          b(i) = f(a(i))
        Leaf(b)
      case Node(l, r) =>
        val (lb, rb) = parallel(mapTreePar(l,f), mapTreePar(r, f))
        Node(lb, rb)

    }

  }



}
