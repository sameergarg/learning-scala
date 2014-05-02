package katas

import org.scalatest.{Matchers, FunSuite}
import org.scalatest.prop.TableDrivenPropertyChecks._
import Recursion._

class ParenthesesBalancingSuite extends FunSuite with Matchers {

  val balancedParenthesesString =
  Table(
    ("stringToCheck"),
    (""),
    ("(if (zero? x) max (/ 1 x))"),
    ("I told him (that it’s not (yet) done). (But he wasn’t listening)"),
    ("Hello")
  )


  val unbalancedParenthesesString =
    Table(
      ("stringToCheck"),
      ("("),
      (")(Hello)"),
      ("())("),
      (":-)")
    )

  test("should detect balanced parentheses") {
    forAll(balancedParenthesesString) { (stringToCheck: String) =>
      balance(stringToCheck.toList) should be(true)
    }
  }

  test("should detect non balanced parentheses") {
    forAll(unbalancedParenthesesString) { (stringToCheck: String) =>
      balance(stringToCheck.toList) should be(false)
    }
  }

}
