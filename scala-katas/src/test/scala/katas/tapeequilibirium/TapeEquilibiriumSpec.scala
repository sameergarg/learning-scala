package katas.tapeequilibirium

import org.scalatest.{Matchers, FlatSpec, FunSuite}

/**
 *
 */
class TapeEquilibiriumSpec extends FlatSpec with Matchers {

  "Tape equilibirium" should "find the minimum of all difference between the partial sum of array at different possible index" in {
    TapeEquilibirium.solution(Array(3,1,2,4,3), 5) shouldBe 1
    TapeEquilibirium.solution(Array(3,1,2,4,7), 5) shouldBe 3
  }

}
