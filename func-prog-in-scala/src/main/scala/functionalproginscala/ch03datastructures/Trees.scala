package functionalproginscala.ch03datastructures

sealed trait Tree[+A]

case class Branch[A](left: Tree[A], right: Tree[A]) extends Tree[A]

case class Leaf[A](value: A) extends Tree[A]

object Trees {

  /**
   * 3.25
   * counts the number of nodes (leaves and branches) in a tree.
   * @param tree
   * @tparam A
   * @return
   */
  def size[A](tree: Tree[A]): Int = tree match {
    case Leaf(_) => 1
    case Branch(left, right) => 1 + size(left) + size(right)
  }

  /**
   * 3.26
   * Write a function maximum that returns the maximum element in a Tree[Int].
   */
  def maximum(tree: Tree[Int]): Int = tree match {
    case Leaf(value: Int) => value
    case Branch(left: Tree[Int], right: Tree[Int]) => maximum(left) max maximum(right)
  }

  /**
   * 3.27
   * The maximum path length from the root of a tree to any leaf.
   */
  def depth[A](tree: Tree[A]): Int = tree match {
    case Leaf(_) => 1
    case Branch(l,r) => 1 + depth(l) max depth(r)
  }

  /**
   * 3.28
   * modifies each element in a tree with a given function
   */
  def map[A,B](tree: Tree[A])(f: A => B): Tree[B] = tree match {
    case Leaf(a) => Leaf(f(a))
    case Branch(l,r) => Branch(map(l)(f), map(r)(f))
  }

  /**
   * 3.29
   * Generalize size, maximum, depth, and map, writing a new function fold that abstracts
   * over their similarities. Reimplement them in terms of this more general function. Can
   * you draw an analogy between this fold function and the left and right folds for List
   */
  def fold[A,B](tree: Tree[A])(f: A=>B)(g: (B,B)=>B): B = tree match {
    case Leaf(a) => f(a)
    case Branch(l,r) => g(fold(l)(f)(g),fold(r)(f)(g))
  }
  def sizeViaFold[A](t: Tree[A]): Int =
    fold(t)(a => 1)(1 + _ + _)

  def maximumViaFold(t: Tree[Int]): Int =
    fold(t)(a => a)(_ max _)

  def depthViaFold[A](t: Tree[A]): Int =
    fold(t)(a => 0)((d1,d2) => 1 + (d1 max d2))

  def mapViaFold[A,B](t: Tree[A])(f: A=>B): Tree[B] = {
    fold(t)(a=>Leaf(f(a)): Tree[B])(Branch(_,_))
  }

}
