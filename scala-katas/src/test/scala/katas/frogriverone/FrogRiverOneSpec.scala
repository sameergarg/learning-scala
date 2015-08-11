package katas.frogriverone

import katas.frogriverone.FrogRiverOne.{solutionWithSideEffects, usingTailRec, pureFunctionalSolution}
import org.scalatest.{Matchers, FlatSpec}

/**
 *
 */
class FrogRiverOneSpec extends FlatSpec with Matchers {

  FrogRiverOne.solutions.foreach{ case (key, function) =>
    "FrogRiverOne" should s"${key} - find the earliest time when leaves appear in every position across the river" in new FrogRiverOne(function){
      solution(5, Array(1,3,1,4,2,3,5), 7) shouldBe 6
      solution(5, Array(1,3,1,4,2,3,5,4), 8) shouldBe 6
      solution(4, Array(1,3,1,4,2,3,5,4), 8) shouldBe 4

    }

    it should s"${key} - return -1 if no path exists" in new FrogRiverOne(function){
      solution(6, Array(1,3,1,4,2,3,5,4), 8) shouldBe -1

    }
  }




}
