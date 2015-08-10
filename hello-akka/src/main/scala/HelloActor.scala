import akka.actor.{Actor, Props, ActorSystem}
import akka.actor.Actor.Receive

/**
 *
 */
class HelloActor(from: String) extends Actor {

  override def receive: Receive = {
    case "hello" => println(s"hello $from")
    case _ => println(s"i don't know $from")
  }
}

class PingPongActor extends Actor {
  
  override def receive: Actor.Receive = {
    case "ping" => println("pong")
    case _ => println("fancy ping pong?")
  }
}

object Main extends App {
  val helloWorldActorsystem = ActorSystem("HelloWorld")
  val helloActor = helloWorldActorsystem.actorOf(Props(new HelloActor("sameer")), name = "helloactor")
  helloActor ! "hello"
  helloActor ! "can you help me"

  val pingPongActorSystem = ActorSystem("pingPong")
  val pingPongActor = pingPongActorSystem.actorOf(Props(new PingPongActor), name="pingPongActor")
  pingPongActor ! "ping"
  pingPongActor ! "how are you"
}
