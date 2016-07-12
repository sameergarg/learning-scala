package quickcheck

import common._

import org.scalacheck._
import Arbitrary._
import Gen._
import Prop._
import Math._

abstract class QuickCheckHeap extends Properties("Heap") with IntHeap {

  lazy val genHeap: Gen[H] = for{
    n <- arbitrary[A]
    h <- oneOf(Gen.const(empty), genHeap)
  } yield insert(n, h)

    implicit lazy val arbHeap: Arbitrary[H] = Arbitrary(genHeap)

  property("gen1") = forAll { (h: H) =>
    val m = if (isEmpty(h)) 0 else findMin(h)
    findMin(insert(m, h)) == m
  }

  property("minimum of two") = forAll { (v1: A, v2: A) =>
    val heap: H = insert(v2, insert(v1, empty))

    findMin(heap) == min(v1, v2)
  }

  property("insert an element into an empty heap, then delete the minimum") = forAll { element: A =>
    isEmpty(deleteMin(insert(element, empty)))
  }

  property("get a sorted sequence of elements when continually finding and deleting minima") = forAll { (elements: List[A]) =>

    def insertAll(acc: H, reducingElements:  List[A]): H = reducingElements match {
      case Nil => acc
      case e::es => insertAll(insert(e, acc), es)
    }

    val heap: H = insertAll(empty, elements)

    def orderByMin(reducingHeap: H, orderedElements: List[A]): List[A] = isEmpty(reducingHeap) match {
      case true => orderedElements
      case false => val min = findMin(reducingHeap)
        orderByMin(deleteMin(reducingHeap), min :: orderedElements)
    }

    orderByMin(heap, List.empty) == elements.sorted.reverse
  }

  property("finding and deleting minimums of arbitrary heap") = forAll { h: H =>
    def listAfterDeletingMin(reducingHeap: H): List[A] = isEmpty(reducingHeap) match {
      case true => Nil
      case false => findMin(reducingHeap):: listAfterDeletingMin(deleteMin(reducingHeap))
    }

    listAfterDeletingMin(h) == listAfterDeletingMin(h).sorted
  }



  property("minimum of the melding of any two heaps should return a minimum of one or the other") = forAll{ (h1: H, h2: H) =>
    min(findMin(h1), findMin(h2)) == findMin(meld(h1, h2))
  }

}
