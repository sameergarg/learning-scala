package taglessFinal

import cats.Monad

import scala.concurrent.{Await, Future}
import scala.util.Random
import cats.implicits._
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * https://vimeo.com/216470124
  * https://www.beyondthelines.net/programming/introduction-to-tagless-final/
  */

object TFExample extends App {

  import taglessFinal.TFExample.PersonReader._

  //algebra
  trait PersonReader[F[_]] {
    def readName: F[Name]

    def readAge: F[Age]

    def readPerson(name: Name, age: Age): F[Person]
  }

  object PersonReader {
    final case class Name(value: String) extends AnyVal
    final case class Age(value: Int) extends AnyVal
    final case class Person(name: Name, age: Age)
  }

  //program
  class PersonGreeter[F[_]: Monad](interpreter: PersonReader[F]) {
    import interpreter._

    def greetPerson(message: String): F[String] =
      for {
        name <- readName
        age <- readAge
        person <- readPerson(name, age)
      } yield (s"$message for $person")

  }


  //interpreter
  val futureInterpreter = new PersonReader[Future] {

    override def readName = Future {
      Name(Random.alphanumeric.take(5).mkString)
    }

    override def readAge = Future {
      Age(Random.nextInt(100))
    }

    override def readPerson(name: Name, age: Age) = Future {
      Person(name, age)
    }
  }

  (1 to 10).foreach(i => println(Await.result(new PersonGreeter(futureInterpreter).greetPerson(i.toString), 2.seconds)))
}
