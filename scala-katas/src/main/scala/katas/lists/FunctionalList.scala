package katas.lists

trait FunctionalList[+T] {

  def isEmpty: Boolean

  def head: T

  def tail: FunctionalList[T]

}


