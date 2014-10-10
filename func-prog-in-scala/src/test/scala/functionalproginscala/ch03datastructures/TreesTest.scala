package functionalproginscala.ch03datastructures

import functionalproginscala.ch03datastructures.Branch
import org.scalatest.{Matchers, FunSuite}

class TreesTest extends FunSuite with Matchers{

  import Trees._

  val tree = Branch(Branch(Leaf(4), Leaf(6)), Leaf(5))

  test("count number of nodes") {
    Trees.size(Leaf(4)) should be(1)
    Trees.size(Branch[Int](Leaf(4), Leaf(5))) should be(3)
    Trees.size(tree) should be(5)
  }

  test("maximum element in a Tree ") {
    maximum(tree) should be(6)
  }

  test(" maximum path length from the root of a tree to any leaf") {
    depth(tree) should be(3)
    depth(Leaf(4)) should be(1)
  }

  test("modifies each element in a tree with a given function") {
    map(tree)(x=>(x+1).toString) should be(Branch(Branch(Leaf("5"), Leaf("7")), Leaf("6")))
  }

  test("implement maximum using fold") {
    //size
    fold[Int,Int](tree)(a=>a)(_ max _) should be(6)
  }

  test("implement size using fold") {
    //size
    fold(tree)(a=>1)(1 + _ + _) should be(5)
  }

  test("implement depth using fold") {
    fold(tree)(a=>1)(1 + _ max _) should be(3)
  }

  test("implement map using fold") {
    mapViaFold(tree)(x=>(x+1).toString) should be(Branch(Branch(Leaf("5"), Leaf("7")), Leaf("6")))
  }

}
