import scala.annotation.tailrec

object Factorial {

  def factorial(n: Int) : Int= {

    @tailrec
    def loop(m: Int, acc: Int) : Int = {
      if(m<2)
        acc
      else {
        loop(m-1, acc*m)
      }
    }
    loop(n,1)
  }

  factorial(4)
  factorial(10)
}