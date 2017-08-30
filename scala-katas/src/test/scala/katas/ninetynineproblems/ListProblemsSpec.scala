package katas.ninetynineproblems

import org.scalatest.{Matchers, WordSpec}
import ListProblems._

/**
  *
  */
class ListProblemsSpec extends WordSpec with Matchers {

  "List problems" should {

    "Remove the Kth element from a list" in {
      removeAt(1, List('a, 'b, 'c, 'd))shouldBe ('b, List('a, 'c, 'd))
    }

    "Insert an element at a given position into a list" in {
      insertAt('new, 1, List('a, 'b, 'c, 'd)) shouldBe List('a, 'new, 'b, 'c, 'd)
    }

    "Create a list containing all integers within a given range." in {
      range(4, 9)shouldBe List(4, 5, 6, 7, 8, 9)
    }

    "Extract a given number of randomly selected elements from a list." in {
      val fromList = List('a, 'b, 'c, 'd, 'f, 'g, 'h)
      val selected: List[Symbol] = randomSelect(3, fromList)
      selected.forall(fromList.contains) shouldBe true
    }

    "P24 (*) Lotto: Draw N different random numbers from the set 1..M." in {
      val draw = lotto(6, 49)
      draw.size  shouldBe 6
      draw.forall(_ <= 49) shouldBe true
    }
  }
}
