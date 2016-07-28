package reductions

import scala.annotation._
import org.scalameter._
import common._

object ParallelParenthesesBalancingRunner {

  @volatile var seqResult = false

  @volatile var parResult = false

  val standardConfig = config(
    Key.exec.minWarmupRuns -> 40,
    Key.exec.maxWarmupRuns -> 80,
    Key.exec.benchRuns -> 120,
    Key.verbose -> true
  ) withWarmer(new Warmer.Default)

  def main(args: Array[String]): Unit = {
    val length = 100000000
    val chars = new Array[Char](length)
    val threshold = 10000
    val seqtime = standardConfig measure {
      seqResult = ParallelParenthesesBalancing.balance(chars)
    }
    println(s"sequential result = $seqResult")
    println(s"sequential balancing time: $seqtime ms")

    val fjtime = standardConfig measure {
      parResult = ParallelParenthesesBalancing.parBalance(chars, threshold)
    }
    println(s"parallel result = $parResult")
    println(s"parallel balancing time: $fjtime ms")
    println(s"speedup: ${seqtime / fjtime}")
  }
}

object ParallelParenthesesBalancing {

  /** Returns `true` iff the parentheses in the input `chars` are balanced.
   */
  //using double accumulator
  def balance(chars: Array[Char]): Boolean = {
    usingDoubleAcc(chars)
  }

  //using single
  def balance2(chars: Array[Char]): Boolean = {
    usingSingleAcc(chars)
  }

  private def usingDoubleAcc(chars: Array[Char]): Boolean = {
    def loop(openParenAcc: Int, closeParenAcc: Int, remChars: List[Char]): Boolean = remChars match {
      case _ if (openParenAcc - closeParenAcc) < 0 => false
      case Nil => openParenAcc - closeParenAcc == 0
      case h :: tail if (h == '(') => loop(openParenAcc + 1, closeParenAcc, tail)
      case h :: tail if (h == ')') => loop(openParenAcc, closeParenAcc + 1, tail)
      case h :: tail => loop(openParenAcc, closeParenAcc, tail)
    }

    loop(0, 0, chars.toList)
  }

  private def usingSingleAcc(chars: Array[Char]): Boolean = {
    def loop(acc: Int, remChars: List[Char]): Int = remChars match {
      case Nil => acc
      case _ if (acc < 0) => acc
      case head :: tail if (head == '(') => loop(acc + 1, tail)
      case head :: tail if (head == ')') => loop(acc - 1, tail)
      case head :: tail => loop(acc, tail)

    }

    loop(0, chars.toList) == 0
  }

  /** Returns `true` iff the parentheses in the input `chars` are balanced.
   */
  def parBalance(chars: Array[Char], threshold: Int): Boolean = {

    def traverse(idx: Int, until: Int, openParen: Int, closeParen: Int) : (Int, Int) =
      if(idx < until){
        chars(idx) match {
          case '(' => traverse(idx+1, until, openParen+1, closeParen)
          case ')' =>
            if(openParen > 0)
              traverse(idx+1, until, openParen - 1, closeParen) //close paren belongs to same part of characters
            else
              traverse(idx+1, until, openParen, closeParen+1) // close paren matches some other open paren outside the current chars
          case _ => traverse(idx+1, until, openParen, closeParen)
        }
      }else
        (openParen, closeParen)

    def reduce(from: Int, until: Int) :  (Int, Int) = {
      val size = until-from

      if(size <= threshold) {
        traverse(from, until, 0, 0)
      } else {
        val mid = from + size/2
        val ((openParen1,closeParen1), (openParen2, closeParen2)) =
          parallel(
           reduce(from, mid),
           reduce(mid, until)
        )

        if(openParen1 > 0)
          (openParen1 - closeParen2 + openParen2, closeParen1)
        else
          (openParen1+openParen2, closeParen1+closeParen2)

      }
    }

    reduce(0, chars.length) == (0, 0)
  }

  // For those who want more:
  // Prove that your reduction operator is associative!

}
