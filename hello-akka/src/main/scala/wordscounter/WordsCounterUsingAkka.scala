package wordscounter

import akka.actor._
import akka.actor.Actor.Receive
import akka.util.Timeout

import scala.concurrent.Future
import scala.io.Source
import scala.concurrent.duration._

/**
 *
 */
case class LineMessage(line: String)
case class WordsInLineMessage(words: Int)
case object CountWordsMessage

class WordsCounterActor extends Actor {
  
  override def receive: Receive = {
    case LineMessage(line) => {
      println(s"received line message for line $line")
      sender ! WordsInLineMessage(line.split("\\W+").size)
    }
    case _ => {
      println("unsupported message")
      0
    }
  }
}

class LineReaderActor(fileName: String) extends Actor {
  private var linesRead = 0
  private var linesProcessed = 0
  private var totalWords = 0
  private var mainSender:ActorRef = _

  override def receive = {
    case CountWordsMessage => {
      println("received count words message")
      mainSender = sender
      Source.fromFile(fileName).getLines().foreach{ line =>
        linesRead+=1
        val wordsCounterActor = context.actorOf(Props(new WordsCounterActor))
        println(s"sending line message for line: $line ")
        wordsCounterActor ! LineMessage(line)
      }
    }
    case WordsInLineMessage(words) => {
      println("counting words in line message")
      linesProcessed+=1
      totalWords += words
      if(linesRead == linesProcessed){
        mainSender ! totalWords
      }
    }   
  }
  
}

object WordsInFileCounter extends App {

  import akka.dispatch.ExecutionContexts._
  import akka.pattern.ask
  println("starting actor system")
  implicit val ec = global
  val system = ActorSystem("actorSystem")
  val actor: ActorRef = system.actorOf(Props(new LineReaderActor("Words.txt")))

  implicit val timeOut = Timeout(25 seconds)
  val future = actor ? CountWordsMessage
  future map {result =>
    println(s"Total number of words $result")
    system.shutdown()
  }
  
}