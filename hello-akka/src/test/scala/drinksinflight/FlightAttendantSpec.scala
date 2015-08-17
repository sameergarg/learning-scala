package drinksinflight

/**
 *
 */

object FlightAttendantSUT {

  def apply() = new FlightAttendant with AttendantResponsiveness{
    override val maxResponseTimeInMillis: Int = 1
  }
}

import akka.actor.ActorSystem
import akka.actor.Props
import akka.testkit.ImplicitSender
import akka.testkit.TestActorRef
import akka.testkit.TestKit
import com.typesafe.config.ConfigFactory
import org.scalatest.Matchers

class FlightAttendantSpec extends TestKit(ActorSystem("FlightAttendantSpec", ConfigFactory.parseString("akka.scheduler.tick-duration = 1ms")))
                          with org.scalatest.FlatSpecLike
                          with ImplicitSender
                          with Matchers {
  import FlightAttendant._

  "flight attendant" should "serve drink when requested" in {
    val actor = TestActorRef(Props(FlightAttendantSUT()))

    actor ! DrinkRequest("coke")

    expectMsg(Drink("coke"))
  }
}
