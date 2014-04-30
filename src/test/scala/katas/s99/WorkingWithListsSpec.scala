package katas.s99

import org.scalatest.{Matchers, FlatSpec}

/**
 *
 * User: sameer
 * Date: 18/03/2014
 * Time: 21:44
 */
class WorkingWithListsSpec extends FlatSpec with Matchers {

  "List" should "identify last element" in {
    val list = List(1, 2, 3, 4)

    list.last should be(4)
    WorkingWithLists lastElement (list) should be(4)
  }

}
