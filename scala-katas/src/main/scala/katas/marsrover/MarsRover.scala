package katas.marsrover

import katas.marsrover.MarsRover._

/**
 *
 * Develop an api that moves a rover around on a grid.
 * You are given the initial starting point (x,y) of a rover and the direction (N,S,E,W) it is facing.
 * The rover receives a character array of commands.
 * Implement commands that move the rover forward/backward (f,b).
 * Implement commands that turn the rover left/right (l,r).
 * Implement wrapping from one edge of the grid to another. (planets are spheres after all)
 * Implement obstacle detection before each move to a new square. If a given sequence of commands encounters an obstacle,
 * the rover moves up to the last possible point and reports the obstacle.
 * Example: The rover is on a 100x100 grid at location (0, 0) and facing NORTH. The rover is given the commands
 * "ffrff" and should end up at (2, 2)
 * Tips: use multiple classes and TDD
- See more at: http://craftsmanship.sv.cmu.edu/katas/mars-rover-kata#sthash.1vLxkwLc.dpuf
 * User: sameer
 * Date: 30/10/2014
 * Time: 21:36
 */

class MarsRover(planet: Planet) {

  def move(position: Position, directions: Directions): (Position, Option[Obstacle]) = {

    def movePosition(pos: Position, dir: Directions): Position = {
      (pos.facing, dir) match {
        case (_, Nil) => pos
        case (facing, direction) if (moveUp(facing, direction.head)) => movePosition(pos.copy(location = pos.location.copy(y = pos.location.y + 1)), direction.tail)
        case (facing, direction) if (moveDown(facing, direction.head)) => movePosition(pos.copy(location = pos.location.copy(y = pos.location.y - 1)), direction.tail)
        case (facing, direction) if (moveForward(facing, direction.head)) => movePosition(pos.copy(location = pos.location.copy(x = pos.location.x + 1)), direction.tail)
        case (facing, direction) if (moveBackward(facing, direction.head)) => movePosition(pos.copy(location = pos.location.copy(x = pos.location.x - 1)), direction.tail)
      }
    }

    (movePosition(position, directions), None)
  }

  private[marsrover] def moveUp(facing: DirectionFacing, direction: Direction) = (facing, direction) match {
    case (E, L) | (N, F) | (W, R) | (S, B) => println("UP"); true
    case _ => false
  }

  private[marsrover] def moveDown(facing: DirectionFacing, direction: Direction) = (facing, direction) match {
    case (E, R) | (N, B) | (W, L) | (S, F) => println("DOWN"); true
    case _ => false
  }

  private[marsrover] def moveForward(facing: DirectionFacing, direction: Direction) = (facing, direction) match {
    case (E, F) | (N, R) | (W, B) | (S, L) => println("FORWARD"); true
    case _ => false
  }

  private[marsrover] def moveBackward(facing: DirectionFacing, direction: Direction) = (facing, direction) match {
    case (E, B) | (N, L) | (W, F) | (S, R) => println("BACKWARD"); true
    case _ => false
  }

}

object MarsRover {

  sealed trait DirectionFacing

  case object N extends DirectionFacing

  case object S extends DirectionFacing

  case object E extends DirectionFacing

  case object W extends DirectionFacing

  sealed trait Direction

  case object F extends Direction

  case object B extends Direction

  case object L extends Direction

  case object R extends Direction

  case class Coordinates(x: Int, y: Int)

  case class Position(location: Coordinates, facing: DirectionFacing)

  case class Grid(rows: Int, cols: Int)

  case class Planet(grid: Grid, obstacles: List[Obstacle])

  type Directions = List[Direction]
  val Directions = List

  type Obstacle = Coordinates


}
