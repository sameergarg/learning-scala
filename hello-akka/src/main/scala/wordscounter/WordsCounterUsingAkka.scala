package wordscounter

import akka.actor._
import akka.actor.Actor.Receive
import akka.util.Timeout

import scala.io.Source
import scala.concurrent.duration._

/**
 *
 */
case class LineMessage(line: String)
case class WordsInLineMessage(words: Int)
case class CountWordsMessage

class WordsCounterActor extends Actor {
  
  override def receive: Receive = {
    case LineMessage(line) => WordsInLineMessage(line.split("\\W+").size)
  }
}

class LineReaderActor(fileName: String) extends Actor {
  private var linesRead = 0
  private var linesProcessed = 0
  private var totalWords = 0
  
  override def receive: Actor.Receive = {
    case CountWordsMessage => {
      Source.fromFile(fileName).getLines().map{
        linesRead+=1
        val wordsCounterActor = context.actorOf(Props(new WordsCounterActor), "wordsCounterActor")
        wordsCounterActor ! LineMessage(_)
      }
    }
    case WordsInLineMessage(words) => {
      linesProcessed+=1
      totalWords += words
      if(linesRead == linesProcessed){
        sender ! totalWords
      }
    }   
  }
  
}

object WordsInFileCounter extends App {

  import akka.dispatch.ExecutionContexts._

  implicit val ec = global
  val system = ActorSystem("actorSystem")
  val actor = system.actorOf(Props(new LineReaderActor("Words.txt")))

  implicit val timeOut = Timeout(25 seconds)
  actor ? CountWordsMessage()
  
}