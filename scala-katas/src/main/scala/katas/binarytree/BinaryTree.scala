package katas.binarytree

import java.util.concurrent.atomic.AtomicInteger

import scala.concurrent.duration._
import scala.concurrent.{Await, ExecutionContext, Promise, Future}

object BinaryTree {

  sealed trait Tree[+T] {

    def map[R](f: T => R):Tree[R]

    def mapFuture[R](f: T => R)(implicit ec: ExecutionContext, timeOut: Duration): Tree[Future[R]]

    def mapPromise[R](f: T=>R)(tree: Tree[Future[R]])(implicit ec: ExecutionContext, timeOut: Duration): Future[Tree[R]]

    def size : Int

  }

  case object Empty extends Tree[Nothing] {

    override def map[R](f: (Nothing) => R): Tree = Empty

    override def mapFuture[R](f: (Nothing) => R)(implicit ec: ExecutionContext, timeOut: Duration): Tree[Future[R]] = Empty

    override def mapPromise[R](f: (Nothing) => R)(tree: Tree[Future[R]])(implicit ec: ExecutionContext, timeOut: Duration): Future[Tree[R]] = Future(Empty)

    override def size = 0
  }

  case class Node[+T](value: T, left: Tree[T], right: Tree[T]) extends Tree[T] {

    override def map[R](f: T => R) = {
      Node(
        f(value),
        left.map(f),
        right.map(f)
      )
    }

    override def mapFuture[R](f: (T) => R)(implicit ec: ExecutionContext, timeOut: Duration): Tree[Future[R]] = {
      Node(
        Future { f(value)},
        left.mapFuture(f),
        right.mapFuture(f)
      )
    }


    override def mapPromise[R](f: (T) => R)(tree: Tree[Future[R]])(implicit ec: ExecutionContext, timeOut: Duration): Future[Tree[R]] = {
      val promise = Promise[Tree[R]]
      val treeSize = new AtomicInteger(size)
      for {
        future <- tree
        treeValue <- future
      } if (treeSize.decrementAndGet() == 0) {
        promise.success(tree.map (Await.result(_, 0 seconds)))
      }
      promise.future
    }

    override def size: Int = this match {
      case Empty => 0
      case Node => 1 + left.size + right.size
    }
  }

  def build[T](height: Int)(node : => T): Tree[T] = height match {
    case 1 => Node(node, Empty, Empty)
    case _ => Node(node, build(height-1)(node), build(height-1)(node))
  }

}
