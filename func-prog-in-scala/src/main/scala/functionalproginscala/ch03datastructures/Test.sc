import scala.annotation.tailrec

object Lists {

  sealed trait List[+A]

  case object Nil extends List[Nothing]

  case class Cons[+A](head: A, tail: List[A]) extends List[A]

  object FuncList {

    def apply[A](as: A*): List[A] =
      if (as.isEmpty) Nil
      else Cons(as.head, apply(as.tail: _*))

  }
  import FuncList._

  /**
   * 3.1
   */
  val funcList = FuncList(1, 2, 3, 4)
}
