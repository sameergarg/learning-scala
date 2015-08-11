package katas.frogriverone {

import scala.annotation.tailrec

class FrogRiverOne(f: (Int, Array[Int], Int) => Int) {

    def solution(x: Int, positions: Array[Int], positionsSize: Int) = {
      f(x, positions, positionsSize)
    }


  }

  object FrogRiverOne {
    lazy val solutions = Map(
      "usingTailRec" -> usingTailRec,
      "solutionWithSideEffects" -> solutionWithSideEffects,
      "pureFunctionalSolution" -> pureFunctionalSolution)

    @tailrec
    val usingTailRec: (Int, Array[Int], Int) => Int = {
      (x, positions, positionsSize) =>

        def loop(currentIndex: Int, acc: Set[Int]): Int = currentIndex match {
          case index if(acc.size == x) => currentIndex -1
          case index if(currentIndex == positionsSize) => -1
          case index => loop(currentIndex+1, acc + positions(index))
        }

        loop(0, Set.empty)
    }

    val solutionWithSideEffects: (Int, Array[Int], Int) => Int = {
      (x, positions, positionsSize) =>
        var timings = Map[Int, Int]()

        def extractIndex: ((Int, Int)) => Unit = {
          pair => {
            if (pair._1 <= x && !timings.contains(pair._1))
              timings += (pair._1 -> pair._2)
          }
        }

        positions.zipWithIndex.foreach{
          extractIndex
        }

        if(timings.size < x) -1 else timings.values.max
    }

    val pureFunctionalSolution: (Int, Array[Int], Int) => Int = {
      (x, positions, positionsSize) =>
        val path = positions.zipWithIndex
          .filter(_._1 <= x)
          .groupBy(_._1)
          .mapValues{ _.map(_._2)
          .min
        }
        if(path.size < x) -1 else path.values.max
    }
  }

}




