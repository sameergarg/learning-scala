package wordscounter

import scala.io.Source

/**
 *
 */
object WordCounter extends App {

  val words = Source.fromFile("Words.txt")
    .getLines()
    .map(_.split("\\W+").size)
    .sum

  println(s"Number of words are $words")
}
