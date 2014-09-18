import scala.annotation.tailrec

object HigherOrderFunctions {
  /**
   * 2.2 Implement isSorted, which checks whether an Array[A] is sorted according to a
   * given comparison function:
   * def isSorted[A](as: Array[A], ordered: (A,A) => Boolean): Boolean
   */
  def isSorted[A](as: Array[A], ordered: (A,A) => Boolean): Boolean = {
    @tailrec
    def loop(as: Array[A], isOrdered: Boolean): Boolean = {
      if(!isOrdered || as.length < 2){
        isOrdered
      } else {
        loop(as.drop(2), ordered(as(0), as(1)))
      }
    }
    loop(as, true)
  }

   def isOrderedInt(a:Int, b:Int): Boolean = {
    a < b
  }

  isSorted(Array(1), isOrderedInt)
  isSorted(Array(1,2,3,4,5,6), isOrderedInt)
  isSorted(Array(2,1), isOrderedInt)
  isSorted[Int](Array(1,2,3,2,4), isOrderedInt)
  isSorted[String](Array("ant","bee","cat","delta"), (a ,b) => a<b)
  isSorted[String](Array("ant","bee","cat","apple"), (a ,b) => a>b)


  /**
   * 2.3 Currying which converts a function f of two arguments
   * into a function of one argument that partially applies f. Here again there’s only one
   * implementation that compiles. Write this implementation
   */
  def curry[A,B,C](f: (A, B) => C): A => (B => C) = {
    //(a:A) => f(a, _)
    (a: A) => (b:B) => f(a,b)
  }

//  val curryFunc[Int, String](a, b) = {2}


  curry[Int, String, Int] ((a, b)=> 2)

  /**
   * 2.4 Implement uncurry, which reverses the transformation of curry. Note that since =>
   * associates to the right, A => (B => C) can be written as A => B => C.
   */
  def uncurry[A,B,C](f: A => B => C): (A, B) => C = {
    (a: A, b:B) => f(a)(b)
  }


  /**
   * 2.5 Implement the higher-order function that composes two functions.
   * def compose[A,B,C](f: B => C, g: A => B): A => C
   */
  def compose[A,B,C](f: B => C, g: A => B): A => C = {
    g andThen f
  }

  /**
   *
   */

}