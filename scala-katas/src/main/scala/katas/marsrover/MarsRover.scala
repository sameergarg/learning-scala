package katas.marsrover

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

  case class Grid(rows:Int, cols:Int)
  case class Location(x: Int, y: Int, direction: DirectionFacing)

  type Directions = List[Direction]

  def move(location: Location, directions: Directions): Location = ???
}
