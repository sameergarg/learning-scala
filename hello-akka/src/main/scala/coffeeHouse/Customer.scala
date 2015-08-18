package coffeeHouse

import akka.actor.{ActorRef, Actor}
import akka.actor.Actor.Receive
import coffeeHouse.Barista.CoffeeRequest
import coffeeHouse.CoffeeHouse.Receipt
import coffeeHouse.Customer.CaffeineRequest

class Customer(coffeeSource: ActorRef) extends Actor {
  import Barista._

  override def receive: Receive = {
    case CaffeineRequest(source) => {
      println(s"I want a $source")
      coffeeSource ! CoffeeRequest(source)
    }
    case (coffee, Receipt(amount)) => println(s"Received $coffee and paid £$amount")
    case a:Any => println(s"I am not getting my coffee ${a.getClass()}")
  }
}

object Customer {

  case class CaffeineRequest(source: String)

  def apply(coffeeSource: ActorRef) = new Customer(coffeeSource)
}
