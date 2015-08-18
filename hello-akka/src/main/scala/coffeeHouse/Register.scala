package coffeeHouse

import akka.actor.Actor
import akka.actor.Actor.Receive
import coffeeHouse.CoffeeHouse.Receipt
import coffeeHouse.Register.Espresso

/**
 *
 */
class Register extends Actor {
  import Register._

  override def receive: Receive = {
    case coffee: Coffee => {
      println(s"${self.path}:printing receipt for $coffee")
      sender ! Receipt(prices(coffee))
    }
  }
}

object Register extends Register {
  sealed trait Coffee
  case object Espresso extends Coffee
  case object Cappuccino extends Coffee
  
  val prices: Map[Coffee, BigDecimal] = Map(Espresso -> BigDecimal(2), Cappuccino -> BigDecimal(3))
}

