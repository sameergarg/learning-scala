/**
 * 2.1 Write a recursive function to get the nth Fibonacci number (http://mng.bz/C29s).
 * The first two Fibonacci numbers are 0 and 1. The nth number is always the sum of the
 * previous two—the sequence begins 0, 1, 1, 2, 3, 5. Your definition should use a
 * local tail-recursive function.
 */
object Fibonacci {

  def at(position: Int) : Int = {
    position match {
      case 0 => 0
      case 1 => 1
      case _ => at(position - 1) + at(position - 2)
    }
  }

  def atWithTailRecursion(position: Int) : Int = {
    def loop(position: Int, secondLast: Int, last: Int): Int = position match {
      case 0 => secondLast
      case _ => loop(position-1, last, secondLast+last)
    }
    loop(position, 0, 1)
  }

  def tailRec(position: Int) = {

  }

  def fib(position:Int , f: Int => Int) = {
    f(position)
  }

  at(0)
  at(1)
  at(2)
  at(3)
  at(4)
  at(5)

  atWithTailRecursion(3)

  fib(5,atWithTailRecursion) == fib(5, at)

}