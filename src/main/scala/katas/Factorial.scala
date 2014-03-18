package katas

/**
 *
 * User: sameer
 * Date: 26/12/2013
 * Time: 22:34
 */
object Factorial {

  def of(n: Int): Int = {
    if (n < 3) n else n * of(n - 1)

  }
}
