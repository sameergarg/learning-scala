package coffeeHouse

import akka.actor.{Props, Actor}
import akka.actor.Actor.Receive
import akka.util.Timeout
import coffeeHouse.Barista.CoffeeRequest
import coffeeHouse.CoffeeHouse.Receipt
import coffeeHouse.Register.{Cappuccino, Espresso}

import scala.concurrent.Future


/**
 *
 */
class Barista extends Actor {

  import akka.pattern.ask
  import akka.pattern.pipe
  import concurrent.duration._
  import Barista._

  implicit val timeout = Timeout(5 seconds)
  implicit val ec = context.dispatcher

  val register = context.actorOf(Props(Register),"register")

  override def receive: Receive = {
    case CoffeeRequest(name)  => {
      println(s"${self.path} :received $name request from ${sender.path}")
      (register ? coffees(name)).map((name, _)) pipeTo sender
    }
    case _ => println("we are closed")
  }
}

object Barista {

  case class CoffeeRequest(name: String)

  private val espresso1: String = "espresso"
  private val cappuccino1: String = "cappuccino"

  val espresso = CoffeeRequest(espresso1)
  val cappuccino = CoffeeRequest(cappuccino1)
  val coffees = Map(espresso1 -> Espresso, cappuccino1 -> Cappuccino)
  def apply() = new Barista()
}
