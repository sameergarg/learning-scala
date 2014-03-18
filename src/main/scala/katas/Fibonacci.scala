package katas

/**
 *
 * User: sameer
 * Date: 26/12/2013
 * Time: 22:11
 */
object Fibonacci {

  def after(n:Int) :Int = {
      if(n==0 || n==1){
        return n
      }

      after(n-1)+after(n-2)
  }

}