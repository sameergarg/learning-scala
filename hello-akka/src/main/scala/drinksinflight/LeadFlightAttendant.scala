package drinksinflight

import akka.actor.{ActorRef, Actor}
import akka.actor.Actor.Receive
/**
  *
 */
trait LeadFlightAttendant extends Actor {


  @throws[Exception](classOf[Exception])
  override def preStart(): Unit = {
    import scala.collection.JavaConverters._
    context.system.settings.config.getStringList("drinksinflight.flightCrew.flightAttendants").asScala

  }

  def getRandomFlightAttendant(): ActorRef = ???

  override def receive: Receive = {
    case CallFlightAttendant => sender ! Attendant(getRandomFlightAttendant())
  }
}

case object CallFlightAttendant

case class Attendant(actorRef: ActorRef)


