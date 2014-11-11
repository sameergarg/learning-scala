package katas.marsrover

import org.scalatest.{Matchers, WordSpec}

/**
 *
 * User: sameer
 * Date: 30/10/2014
 * Time: 21:56
 */
class MarsRoverTest extends WordSpec with Matchers {

  import MarsRover._

  "Mars Rover" when {
    "facing N asked to move F" must {
      "move 1 place forward" in {
        move(Location(0,0,List(F))) should be Location(0,1,N)
      }
    }
  }


}
