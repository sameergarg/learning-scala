package katas.binarytree

/**
 * In computer science, a binary tree is a tree data structure in which each node has at most two children, which are
 * referred to as the left child and the right child.
 */

trait BinaryTree[A] {

  def incl(elem: A): BinaryTree[A]

  def contains(elem: A): Boolean

  def remove(elem: A): BinaryTree[A]
}

case class Node(left: BinaryTree[Int], right: BinaryTree[Int], element: Int) extends BinaryTree[Int] {

  override def incl(elem: Int): BinaryTree[Int] = elem match {
    case x if x < element => Node(left.incl(elem), right, element)
    case x if x > element => Node(left, right.incl(elem), element)
    case _ => this
  }


  override def remove(elem: Int): BinaryTree[Int] = elem match {
    case x if x < element => Node(left.remove(elem), right, element)
    case x if x < element => Node(left, right.remove(elem), element)
    case _ => this
  }

  override def contains(elem: Int): Boolean = {
    if(elem < element)
      left contains elem
    else if(elem > element)
      right contains elem
    else
      true
  }

}

object EmptyNode extends BinaryTree[Int] {

  override def incl(elem: Int): BinaryTree[Int] = Node(EmptyNode, EmptyNode, elem)

  override def contains(elem: Int): Boolean = false

  override def remove(elem: Int) = EmptyNode
}

