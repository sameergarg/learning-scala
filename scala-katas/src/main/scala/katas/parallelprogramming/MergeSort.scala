package katas.parallelprogramming
import katas.parallelprogramming.common._

/**
  * Created by samegarg on 27/07/2016.
  */
object MergeSort {

  def parMergeSort(xs: Array[Int], maxDepth: Int): Unit = {

  }

  /*def sort(from: Int, until: Int, depth: Int): Unit = {
    if(depth == maxDepth)
      quickSort(from, until, until - from)
    else {
      val mid = (until - from)/2
      parallel(sort(from, mid, depth +1), sort(mid, until, depth +1) )
    }

  }*/


}
