import Hangman.Status.Won

import scala.io.Source

/**
  * The computer will select a random word for you and will ask you to guess the word.
  * You can guess one character at a time. Every time you guess a character which occurs in the word,
  * all occurrences of that character will be revealed. If you guess all the characters which occur in the chosen word
  * you win the game. If the number of guesses reaches 10 and you still haven’t guessed all characters, you lose.
  */

object Main extends App {
  import Hangman._

  val readWord = Source.fromResource("Dictionary.txt").getLines()

  val name = scala.io.StdIn.readLine("Enter name: ")

  val wordToGuess = readWord.take(1).toList.head.toString()

  val initial = GameState(word = wordToGuess)

  println(s"Word to guess: ${initial.current} ($wordToGuess)")

  val result = play(initial)

  val message = result.status match {
    case Won => s"${Console.GREEN} $name won !!!. You guessed '${result.word}' correctly. ${Console.RESET}"
    case _ => s"${Console.RED} $name lost. The word was '${result.word}', Your guesses were incorrect [${result.guess.mkString(" ")}] ${Console.RESET}"
  }
    println(message)
}

object Hangman {
  sealed trait Status

  case object Status {
    case object Won extends Status
    case object Lost extends Status
    case object InPlay extends Status
  }

  import Status._

  case class GameState(guess: Set[Char] = Set.empty, word: String, status: Status = InPlay) {

    private def guessedCorrectly = word.forall(guess.contains)

    private def play = (guessedCorrectly, guess.size) match {
      case (true, _) => GameState(guess, word, Won)
      case (false, s) if (s < 10) => this.copy(status = InPlay)
      case (_, _) => this.copy(status = Lost)
    }

    def check(char: Char) = this.copy(guess = guess + char).play

    def current = word.map(c => if(guess.contains(c)) c else "_").mkString(" ")

  }


  def play(state: GameState): GameState = state.status match {
    case InPlay =>
      println(s"Guess a char: ${state.current}")
      val newGuess = scala.io.StdIn.readChar()
      play(state.check(newGuess))
    case _ => state
  }
}


