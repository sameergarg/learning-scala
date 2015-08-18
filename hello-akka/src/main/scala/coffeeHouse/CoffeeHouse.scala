package coffeeHouse

import akka.actor.{Props, ActorSystem}
import coffeeHouse.Barista.CoffeeRequest
import coffeeHouse.Customer.CaffeineRequest

/**
 * Refer:
 * http://danielwestheide.com/blog/2013/03/20/the-neophytes-guide-to-scala-part-15-dealing-with-failure-in-actor-systems.html
 */
object CoffeeHouse extends App {

  case class Receipt(amount: BigDecimal)

  val system = ActorSystem("coffeeHouse")

  //actors created by system are root level actors whose parent is a guardian actor
  val barista = system.actorOf(Props(Barista()),"barista")
  println(s"barista actor path: ${barista.path}")

  val sameer = system.actorOf(Props(classOf[Customer], barista), "customerSameer")
  println(s"sameer customer actor path: ${sameer.path}")

  sameer ! CaffeineRequest("espresso")
}


