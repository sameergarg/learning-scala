package katas

import java.util.NoSuchElementException

object WWL {


    def lastButOneElementFromList(list: List[Int]) :Int = list match {
      case head::rest => lastButOneElementFromList(rest)
      case _::tail::Nil => _
      case _ => throw new NoSuchElementException
    }

  def lastButOneElementFromListUsingInbuiltMethod(list: List[Int]): Int = {
    2
  }

}
