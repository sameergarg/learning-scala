package katas.marsrover

import katas.marsrover.MarsRover._
import org.scalatest.{Matchers, FlatSpec}

/**
 * Created by samegarg on 25/08/15.
 */
class MarsRoverSpec extends FlatSpec with Matchers {

  "Mars Rover" should "be able to define a planet with dimension and obstacles" in {
    val planet = Planet(grid = Grid(10, 5), obstacles = List(Coordinates(5, 2)))
    planet.grid.cols shouldBe 5
    planet.grid.rows shouldBe 10
    planet.obstacles.head.x shouldBe 5
    planet.obstacles.head.y shouldBe 2
  }

  it should "be able to define the rover position with the direction facing" in {
    val position = Position(location = Coordinates(1, 2), facing = E)

    position.location.x shouldBe 1
    position.location.y shouldBe 2
    position.facing shouldBe E

  }

  val planet = Planet(grid = Grid(10, 5), obstacles = List(Coordinates(5, 2)))
  val marsRover = new MarsRover(planet)
  val moveUpPositions = List((E, L), (N, F), (W, R), (S, B))
  val moveDownPositions = List((E, R),(N,B), (W, L), (S, F))
  val moveRightPositions = List((E, F),(N, R), (W, B), (S, L))
  val moveLeftPositions = List((E, B), (N, L), (W, F), (S, R))

  "Mars Rover" should "be able to move up on a planet from a given position" in {
    moveUpPositions.foreach {
      case ((f, d)) =>
        val position = Position(location = Coordinates(1, 2), facing = f)
        val newPosition = marsRover.move(position, MarsRover.Directions(d))

        newPosition._2 shouldBe None
        newPosition._1.location.x shouldBe 1
        newPosition._1.location.y shouldBe 3
    }

  }

  it should "be able to move down on a planet from a given position" in {
    moveDownPositions.foreach {
      case ((f, d)) =>
        val position = Position(location = Coordinates(1, 2), facing = f)
        val newPosition = marsRover.move(position, MarsRover.Directions(d))

        newPosition._2 shouldBe None
        newPosition._1.location.x shouldBe 1
        newPosition._1.location.y shouldBe 1
    }

  }

  it should "be able to move right on a planet from a given position" in {
    moveRightPositions.foreach {
      case ((f, d)) =>
        val position = Position(location = Coordinates(1, 2), facing = f)
        val newPosition = marsRover.move(position, MarsRover.Directions(d))
        newPosition._2 shouldBe None
        newPosition._1.location.x shouldBe 2
        newPosition._1.location.y shouldBe 2
    }
  }

  it should "be able to move left on a planet from a given position" in {
    moveLeftPositions.foreach {
      case ((f, d)) =>
        val position = Position(location = Coordinates(1, 2), facing = f)
        val newPosition = marsRover.move(position, MarsRover.Directions(d))
        newPosition._2 shouldBe None
        newPosition._1.location.x shouldBe 0
        newPosition._1.location.y shouldBe 2
    }
  }


}
