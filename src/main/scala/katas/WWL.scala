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

  def nthElementOfList[A](list:List[A], elementAt:Int) : A = {
    if(list.length < elementAt+1){
      throw new NoSuchElementException
    }
    list(elementAt)
  }

  def nthElementOfListUsingDrop[A](list:List[A], elementAt:Int) : A = {
    if(list.length < elementAt+1){
      throw new NoSuchElementException
    }
    list.drop(elementAt).head
  }

}
