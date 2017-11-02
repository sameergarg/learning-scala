import java.net.InetAddress

import UDPExample.Client.UDPClient
import UDPExample.Server.{UDPServerWithFuture, UDPServerWithStream}
import cats.Id
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{FlatSpec, Matchers}

import scala.concurrent.Future

class UDPExampleSpec extends FlatSpec with Matchers with ScalaFutures {

  "UDP Example" should "obtain results from Future based server" in {
    val server = new UDPServerWithFuture
    val client = new UDPClient(server.port, InetAddress.getLocalHost())
    val times = 2

    (1 to times).foreach{ _ => client.send("hello") }

    val received: Future[List[String]] = server.receiveN(times)

    whenReady(received){ result =>
      result shouldBe List("hello", "hello")
    }
    server.stop
  }

  it should "obtain results from Stream based server" in {
    val server = new UDPServerWithStream
    val client = new UDPClient(server.port, InetAddress.getLocalHost())
    val times = 2

    (1 to times).foreach{ _ => client.send("hello") }
    val received: Stream[Id[String]] = server.receiveN(times)
    received.head shouldBe "hello"
    received.take(2) shouldBe List("hello", "hello")
    received.take(3) shouldBe List("hello", "hello")

  }

}
