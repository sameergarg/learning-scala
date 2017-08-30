package katas.ninetynineproblems

import scala.annotation.tailrec
import scala.util.Random

/**
  *
  */
object ListProblems {

  //P20 (*) Remove the Kth element from a list.
  def removeAt[T](index: Int, from: List[T]): (T, List[T]) = {
    @tailrec
    def loop(curr: Int,  pre:List[T], post: List[T]): (T, List[T]) = (curr, pre, post) match {
      case(_, _, Nil) => throw new IllegalArgumentException("index out of bound")
      case(n, p, l) if(n == 0) => (l.head, p ++ l.tail)
      case(n, p, l) => loop(n-1, p :+ l.head, l.tail)
    }

    loop(index, Nil, from)
  }

  //P21 (*) Insert an element at a given position into a list.
  def insertAt[T](element: T, pos: Int, into: List[T]): List[T] = into.splitAt(pos) match {
    case (Nil, _) if(pos < 0) => throw new NoSuchElementException
    case (pre, post) => pre ++ (element :: post)
    case (_, Nil) => throw new NoSuchElementException
  }

  //P22 (*) Create a list containing all integers within a given range.
  def range(from: Int, until: Int): List[Int] = {
    //(from to until).toList
    List.range(from, until+1)
  }

  //P23 (**) Extract a given number of randomly selected elements from a list.
  def randomSelect[T](count: Int, from: List[T]): List[T] = {
    if(count == 0) Nil
    else {
      val (removed, rem) = removeAt(Random.nextInt(from.size), from)
      removed :: randomSelect(count - 1, rem)
    }
  }

  // P24 (*) Lotto: Draw N different random numbers from the set 1..M.
  def lotto(n: Int, m: Int) = {
    randomSelect(n, (1 to m).toList)
  }

  //Generate the combinations of K distinct objects chosen from the N elements of a list
  def combinations[T](size: Int, fromElements:List[T]) = {
    fromElements.take(3)
    (size, fromElements) match {
      case (0, _) => List.empty
      case (1, Nil) => Nil

    }
  }


}
