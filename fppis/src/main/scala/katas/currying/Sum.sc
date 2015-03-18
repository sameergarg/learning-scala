object Sum {
  /**
   * The sum function uses a linear recursion. Can you write a tailrecursive
   * one
   */

  def id(x: Int) = x

  def sq(x:Int) = x*x

  def sum(f: Int => Int)(a: Int, b: Int): Int = {
      if(a>b) 0 else f(a) + sum(f)(a+1, b)
  }
  def tailRecSum(f: Int => Int)(a: Int, b: Int): Int = {
    def loop(a: Int, acc: Int): Int = {
      if(a>b) acc else loop(a+1, acc+f(a))
    }
    loop(a, 0)
  }

  val sumOfConsecutiveInt = sum(id)_
  val sumOfSquares = sum(sq)_

  sumOfConsecutiveInt(1,4)

  sumOfSquares(1,3)
  tailRecSum(id)(1,4)
  tailRecSum(sq)(1,3)

}