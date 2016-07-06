package katas.streams

import katas.streams.Bloxorz.{Level, Level1}
import org.scalatest.{Matchers, WordSpec}

/**
  * Created by samegarg on 06/07/2016.
  */
class BloxorzSpec extends WordSpec with Matchers {
  val levelVector = Vector(
    Vector('-','-','-','-','-','-'),
    Vector('-','-','S','T','-','-'),
    Vector('-','-','o','o','-','-'),
    Vector('-','-','o','o','-','-'),
    Vector('-','-','-','-','-','-')
  )

  "String parser terrain" when {

    "call terrain Function" should {
      "determine when a position is valid" in new StringParserTerrain {
        override val level: String = ""
        terrainFunction(levelVector)(Pos(1, 2)) shouldBe true
        terrainFunction(levelVector)(Pos(2, 2)) shouldBe true
      }

      "determine when a position is invalid" in new StringParserTerrain {
        override val level: String = ""
        terrainFunction(levelVector)(Pos(0, 0)) shouldBe false
        terrainFunction(levelVector)(Pos(4, 1)) shouldBe false
      }
    }

    "find char is called" should {
      "return position of a char" in new StringParserTerrain {
        override val level: String = ""
        findChar('S',levelVector) should be (Pos(1, 2))
        findChar('T',levelVector) should be (Pos(1, 3))
      }
    }
  }

  class GameDefSUT extends GameDef {
    override val terrain: Terrain = pos => levelVector(pos.x)(pos.y) != '-'
    override val goal: Pos = Pos(1,1)
    override val startPos: Pos = Pos(1,2)
  }

  "Game Def" when {
    "is standing" should {
      "determine if the box is standing or not" in new GameDefSUT {
        Block(Pos(1,1), Pos(1,1)).isStanding should be(true)
        Block(Pos(1,1), Pos(2,1)).isStanding should be(false)
      }
    }

    "is legal" should {
      "determine if the position is legal" in new GameDefSUT {
        Block(Pos(2,2), Pos(2,3)).isLegal shouldBe true
        Block(Pos(0,0), Pos(0,1)).isLegal shouldBe false
      }
    }

    "neighbours" should {
      "return all neighbours" in new GameDefSUT {
        Block(Pos(2,2), Pos(2,3)).neighbors should contain (Block(Pos(2,1), Pos(2,1)), Left)
        Block(Pos(2,2), Pos(2,3)).neighbors should contain (Block(Pos(2,4), Pos(2,4)), Right)
        Block(Pos(2,2), Pos(2,3)).neighbors should contain (Block(Pos(1,2), Pos(1,3)), Up)
        Block(Pos(2,2), Pos(2,3)).neighbors should contain (Block(Pos(3,2), Pos(3,3)), Down)
      }

      "return all legal neighbours" in new GameDefSUT {
        Block(Pos(2,2), Pos(2,3)).legalNeighbors should not contain (Block(Pos(2,1), Pos(2,1)), Left)
        Block(Pos(2,2), Pos(2,3)).legalNeighbors should not contain (Block(Pos(2,4), Pos(2,4)), Right)
        Block(Pos(2,2), Pos(2,3)).legalNeighbors should contain (Block(Pos(1,2), Pos(1,3)), Up)
        Block(Pos(2,2), Pos(2,3)).legalNeighbors should contain (Block(Pos(3,2), Pos(3,3)), Down)
      }
    }
  }

  class SolverSUT extends Solver {
    override val startPos: Pos = Pos(1,2)
    override val terrain: Terrain = pos => levelVector(pos.x)(pos.y) != '-'
    override val goal: Pos = Pos(1,3)
  }

  "Solver" when {
    "list neighbours with history" should {
      "return all valid neighbours with history" in new Level {
        val level =
          """ooo-------
            |oSoooo----
            |ooooooooo-
            |-ooooooooo
            |-----ooToo
            |------ooo-""".stripMargin

        neighborsWithHistory(Block(Pos(1,1),Pos(1,1)), List(Left,Up)).toSet shouldBe Set(
          (Block(Pos(1,2),Pos(1,3)), List(Right,Left,Up)),
          (Block(Pos(2,1),Pos(3,1)), List(Down,Left,Up))
        )
      }
    }

    "new neighbours" should {
      "give only new neighbours" in new SolverSUT {
        newNeighborsOnly(
          Set(
            (Block(Pos(1,2),Pos(1,3)), List(Right,Left,Up)),
            (Block(Pos(2,1),Pos(3,1)), List(Down,Left,Up))
          ).toStream,

          Set(Block(Pos(1,2),Pos(1,3)), Block(Pos(1,1),Pos(1,1)))
        ) shouldBe Set(
          (Block(Pos(2,1),Pos(3,1)), List(Down,Left,Up))
        ).toStream
      }
    }
  }
}
