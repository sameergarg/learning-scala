package katas.balancedIndex

import scala.annotation.tailrec

/**
 *
 */
object BalancedIndex {

  def find(arr: Array[Int], x: Int): Int = {

    @tailrec
    def loop(matchFound: Boolean, index: Int): Int = index match {
      case i if(matchFound) => index -1
      case i if(index >= arr.size) => -1
      case _ => {
        val (left, right) = arr.splitAt(index)
        val matches = left.count(_ == x)
        val mismatches = right.count(_ != x)
        loop(matches > 0 && matches == mismatches, index + 1)
      }
    }

    loop(false, 0)
  }

}
