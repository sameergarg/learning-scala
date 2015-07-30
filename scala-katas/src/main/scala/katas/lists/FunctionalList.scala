package katas.lists

trait FunctionalList[+T] {

  def isEmpty: Boolean

  def head: T

  def tail: FunctionalList[T]

}

case object NilList extends FunctionalList[Nothing] {

  override def isEmpty: Boolean = true

  override def tail: FunctionalList[Nothing] = NilList

  override def head: NilList.type = NilList
}

case class Cons[T](head: T, tail: FunctionalList[T]) extends FunctionalList {

  override def isEmpty: Boolean = false

}
