package katas

import org.scalatest.{FunSuite, Matchers}
import Recursion._
import org.scalatest.prop.TableDrivenPropertyChecks._


/**
 *             1
 *            1 1
 *           1 2 1
 *          1 3 3 1
 *         1 4 6 4 1
 * User: sameer
 * Date: 30/04/2014
 * Time: 20:52
 */
class PascalTriangleSuite extends FunSuite with Matchers {

  val pascalTrianglePositionValues =
    Table (
      ("column", "row", "value"),
      (0,0,1) ,
      (0,1,1),
      (1,1,1),
      (0,2,1),
      (1,2,2),
      (2,2,1),
      (0,3,1),
      (1,3,3),
      (2,3,3),
      (3,3,1),
      (0,4,1),
      (1,4,4),
      (2,4,6),
      (3,4,4),
      (4,4,1)

    )


  test("pascal triangle number at column and row") {
    forAll(pascalTrianglePositionValues) {(column,row,value) =>
      pascal(column, row) should be(value)
      //pascal(1,3)
    }
  }

}

