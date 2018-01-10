package katas.flipcoin


import scala.annotation.tailrec
import scala.util.Random

case class GameState(flips: Int, wins: Int) {
  override def toString: String = s"Flipped: ${flips} times,  won ${wins} times"
}

case object GameState {
  val init = GameState(0,0)
}

trait FlipCoin {

  def flip(r: Random): String = r.nextInt(2) match {
    case 0 => "H"
    case 1 => "T"
  }

  def recordResult(r: Random, choice: String, gameState: GameState): GameState = {
    if(flip(r) == choice)
      GameState(gameState.flips + 1, gameState.wins + 1)
    else GameState(gameState.flips + 1, gameState.wins)
  }

  def prompt: Unit = println("Press 'H' for heads, 'T' for tails, 'N' for new game or 'Q' to quit")

  def read: String = scala.io.StdIn.readLine.trim.toUpperCase

  @tailrec
  final def playTillQuit(r: Random, choice: String, currentGameState: GameState, previousStates: List[GameState]): Unit = choice match {
    case "Q" => println(s"Game over !!! Results: [${previousStates:+ currentGameState mkString(",")}]")
    case "N" =>
      println("Starting fresh game")
      prompt
      val newChoice = read
      playTillQuit(Random, newChoice, GameState.init, previousStates :+ currentGameState)
    case _  =>
      val newState = recordResult(r, choice, currentGameState)
      println(newState)
      prompt
      val newChoice = read
      playTillQuit(Random, newChoice, newState, previousStates)
  }

}


object Main extends App with FlipCoin {
  prompt
  val choice = read

  private val startingState = GameState.init
  private val previousStates = List.empty[GameState]
  playTillQuit(Random, choice, startingState, previousStates)
}
