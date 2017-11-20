package implicits.typeclassimplicits

/**
  * http://baddotrobot.com/blog/2016/08/13/type-classes/
  */
object TypeClassImplicit extends App {

  //interface
  trait NumberLike[T] {
    def plus(a1: T, a2: T): T
    def multiply(a1: T, a2: T): T
  }

  //instances
  object NumberLike {
    //allows look in companion object
    def apply[T: NumberLike]: NumberLike[T] = implicitly

    implicit val intNumberLike = new NumberLike[Int] {
      override def plus(a1: Int, a2: Int): Int = a1 + a2

      override def multiply(a1: Int, a2: Int): Int = a1 * a2
    }

    implicit object StringNumberLike extends NumberLike[String] {
      override def plus(a1: String, a2: String): String = a1 + a2

      override def multiply(a1: String, a2: String): String = a1 + a2 + a1 + a2
    }
  }

  //syntax

  object Syntax {

    implicit class NumberLikeOps[T](t: T) {
      def add(t2: T)(implicit numberLike: NumberLike[T]) = numberLike.plus(t, t2)
    }
  }

  import Syntax._
  println(
    1.add(2)
  )

  println(
    "hello".add("sameer")
  )
}
