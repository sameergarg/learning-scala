package drinksinflight

import akka.actor.Actor
import akka.actor.Actor.Receive


import scala.util.Random

trait AttendantResponsiveness {
  import scala.concurrent.duration._

  val maxResponseTimeInMillis: Int

  def responseDuration = Random.nextInt(maxResponseTimeInMillis).millis
}

trait FlightAttendant extends Actor {
  this: AttendantResponsiveness =>

  import FlightAttendant._

  implicit val ec = context.dispatcher

  override def receive: Receive = {

    case DrinkRequest(drinkName) => context.system.scheduler.scheduleOnce(responseDuration, sender, Drink(drinkName))
  }
}

object FlightAttendant {

  case class DrinkRequest(drinkName: String)
  case class Drink(drinkName: String)

  def apply() = new FlightAttendant with AttendantResponsiveness{
    override val maxResponseTimeInMillis: Int = 5000
  }

}