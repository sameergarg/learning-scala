package recfun

import scala.annotation.tailrec

object Main {
  def main(args: Array[String]) {
    println("Pascal's Triangle")
    for (row <- 0 to 10) {
      for (col <- 0 to row)
        print(pascal(col, row) + " ")
      println()
    }
  }

  /**
   * Exercise 1
   */
    def pascal(c: Int, r: Int): Int = {

    def loop(acc: Int, col: Int, row: Int): Int = {
        if(col == 0 || row == col)
          acc + 1
        else
          loop(acc, col - 1, row -1) + loop(acc, col, row - 1)
      }

      loop(0, c, r)
    }
  
  /**
   * Exercise 2
   */
    def balance(chars: List[Char]): Boolean = {

      def loop(acc: Int, rem: List[Char]): Boolean = {
        if (acc < 0)
          false
        else rem match {
          case Nil => acc == 0
          case '('::tail => loop(acc + 1, tail)
          case ')'::tail => loop(acc - 1, tail)
          case head::tail => loop(acc, tail)
        }
      }

      loop(0, chars)
    }
  
  /**
   * Exercise 3
   */
    def countChange(money: Int, coins: List[Int]): Int = ???
  }
