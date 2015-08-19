package katas.tapeequilibirium

/**
 *
 */
object TapeEquilibirium {

  def solution(a: Array[Int], arrayLength: Int): Int = {
    (1 to arrayLength-1).map{ n =>
      val leftRightPair = a.splitAt(n)
      //println(s"index: $n, sum left:$sumLeft - sumRight:$sumRight is $difference")
      Math.abs(leftRightPair._1.sum - leftRightPair._2.sum)
    }.min
  }

}


