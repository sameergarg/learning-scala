package katas

import scala.annotation.tailrec

object Recursion extends App {

  /*
  The following pattern of numbers is called Pascal’s triangle.

    1
   1 1
  1 2 1
 1 3 3 1
1 4 6 4 1
   ...
The numbers at the edge of the triangle are all 1, and each number inside the triangle is the sum of the two numbers above it.

Write a function that computes the elements of Pascal’s triangle by means of a recursive process.

Do this exercise by implementing the pascal function in Main.scala, which takes a column c and a row r, counting from 0
and returns the number at that spot in the triangle. For example, pascal(0,2)=1, pascal(1,2)=2 and pascal(1,3)=3.
   */
  def pascal(c: Int, r: Int): Int = (c, r) match {

    case (c, r) if (c == 0 || c == r) => 1

    case _ => pascal(c - 1, r - 1) + pascal(c, r-1)

  }


  /*
  Exercise 2: Parentheses Balancing
  Write a recursive function which verifies the balancing of parentheses in a string, which we represent as a List[Char]
  not a String. For example, the function should return true for the following strings:

  (if (zero? x) max (/ 1 x))
  I told him (that it’s not (yet) done). (But he wasn’t listening)
  The function should return false for the following strings:

  :-)
  ())(
  The last example shows that it’s not enough to verify that a string contains the same number of opening and closing
  parentheses.

  Do this exercise by implementing the balance function in Main.scala. Its signature is as follows:
   */
  def balance(chars: List[Char]): Boolean = {
    @tailrec
    def loop(chars: List[Char], parentheses: Int): Boolean = chars match {
      case Nil => parentheses == 0
      case ')' :: tail => if(parentheses==0) false else loop(tail, parentheses-1)
      case '(' :: tail  => loop(tail, parentheses+1)
      case head :: tail  => loop(tail, parentheses)
    }

    loop(chars,0)
  }

  /*
  Excercise 3: Counting change
  Write a recursive function that counts how many different ways you can make change for an amount, given a list of coin
  denominations. For example, there are 3 ways to give change for 4 if you have coins with
  denomiation 1 and 2: 1+1+1+1, 1+1+2, 2+2.

Do this exercise by implementing the countChange function in Main.scala. This function takes an amount to change, and a
list of unique denominations for the coins. Its signature is as follows:

def countChange(money: Int, coins: List[Int]): Int
Once again, you can make use of functions isEmpty, head and tail on the list of integers coins.

Hint: Think of the degenerate cases. How many ways can you give change for 0 CHF? How many ways can you give change
for >0 CHF, if you have no coins?
   */
  def countChange(money: Int, coins: List[Int]): Int = {
    def loop(money: Int, coins: List[Int], ways:Int): Int = coins match {
      case Nil => ways
      case _ if money == 0 => ways+1
      case _ if(money - coins.head < 0 || coins.isEmpty) => 0
      case _ => loop(money-coins.head, coins, ways) +   loop(money, coins.tail, ways)

    }
    val sortedCoins = coins.sorted
    loop(money, sortedCoins, 0)
  }

}
