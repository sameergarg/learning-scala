package katas

import java.util.NoSuchElementException

object WWL {


    def lastButOneElementFromList(list: List[Int]) :Int = list match {
      case secondLast::last::Nil => secondLast
      case _ :: rest => lastButOneElementFromList(rest)
      case _ => throw new NoSuchElementException
    }

  def lastButOneElementFromListUsingInbuiltMethod(list: List[Int]): Int = {
    if(list.isEmpty){
      throw new NoSuchElementException
    }
    list.init.last
  }

}
