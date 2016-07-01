package katas.generators

import scala.util.Random


object FunctionalGenerators {

  trait Generator[+T] {
    self =>

    def generate: T

    def map[S](f: T => S): Generator[S] = new Generator[S] {
      override def generate: S = f(self.generate)
    }

    def flatMap[S](f: T => Generator[S]): Generator[S] = new Generator[S] {
      override def generate: S = f(self.generate).generate
    }
  }

  val integers = new Generator[Int] {
    override def generate: Int = Random.nextInt()
  }

  val booleans: Generator[Boolean] = {
    for(integer <- integers) yield (integer > 0)
  }

  def pairs[T, U](ts: Generator[T], us: Generator[U]): Generator[(T,U)] = {
    for{
      x <- ts
      y <- us
    } yield (x,y)
  }

  def constant[T](x:T) = new Generator[T] {
    override def generate: T = x
  }

  def choose(low: Int, high: Int) = {
    for(x <- integers)yield low + x%(high-low)
  }

  def oneOf[T](xs: T*) = {
    for(x <- choose(0, xs.length)) yield xs(x)
  }

  def lists: Generator[List[Int]] = for {
     isEmpty <- booleans
     list <- if(isEmpty) constant(Nil) else nonEmptyList
  } yield list

  private def nonEmptyList: Generator[List[Int]] = for{
    head <- integers
    tail <- lists
  } yield head :: tail

}

object RandomTreeGenerator {
  import FunctionalGenerators. _

  trait Tree

  case class Inner(left: Tree, right: Tree) extends Tree

  case class Leaf(x: Int) extends Tree

  def trees: Generator[Tree] = for{
    isLeaf <- booleans
    tree <- if(isLeaf) leaf else inner
  } yield tree

  def inner: Generator[Inner] = for{
    left <- trees
    right <- trees
  } yield Inner(left, right)

  private def leaf: Generator[Leaf] = for{
    value <- integers
  } yield Leaf(value)

}

object FunctionalGeneratorsApp extends App {
  (1 to 5) foreach(i => println(FunctionalGenerators.booleans.generate))
}
