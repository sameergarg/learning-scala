package katas.recursion

import katas.recursion.Recursion._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.prop.TableDrivenPropertyChecks._
import org.scalatest.{FunSuite, Matchers}
/**
 * User: sameer
 * Date: 30/04/2014
 * Time: 20:52
 */
@RunWith(classOf[JUnitRunner])
class CountingChangeSuite extends FunSuite with Matchers {

  val countingChangeDataTable =
    Table (
      ( "amount", "listOfCoinsDenomination", "waysToMakeChange"),
      (0,List(),0),
      (1,List(1),1),
      (2,List(1,2),2),
      (4,List(1,2),3),
      (4,List(1,2,3),4),
      (5,List(1,2,3),5)
    )


  test("ways to make change for an amount ") {
    forAll(countingChangeDataTable) {(amount: Int, listOfCoinsDenomination: List[Int], waysToMakeChange: Int) =>
      countChange(amount, listOfCoinsDenomination) should be(waysToMakeChange)
    }
  }

}

