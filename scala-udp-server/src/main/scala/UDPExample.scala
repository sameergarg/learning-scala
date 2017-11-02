import java.net.{DatagramPacket, DatagramSocket, InetAddress}

import cats.Id

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object UDPExample {

  object Server {

    trait UDPServer[T[_], L[_], A] {
      private val server = new DatagramSocket(6789)
      val port = server.getLocalPort

      def stop = server.close()

      def receive: String = {
        val buffer = new Array[Byte](256)
        val packet = new DatagramPacket(buffer, 0, buffer.length)
        server.receive(packet)
        new String(packet.getData, 0, packet.getLength)
      }

      def receiveN(n: Int): T[L[A]]
    }

    class UDPServerWithFuture extends UDPServer[Future, List, String] {
      override def receiveN(n: Int): Future[List[String]] = Future.traverse((1 to n).toList)(_ => Future {
        receive
      })
    }

    class UDPServerWithStream extends UDPServer[Stream, Id, String] {
      override def receiveN(n: Int): Stream[Id[String]] = if(n==0) Stream.empty else receive #:: receiveN(n-1)
    }
  }

  object Client {
    class UDPClient(serverPort: Int, serverHost: InetAddress) {
      def send(message: String) = {
        val connection = new DatagramSocket()
        val messageBytes = message.getBytes()
        val packet = new DatagramPacket(messageBytes, 0, messageBytes.length, serverHost, serverPort)
        connection.send(packet)
        connection.close()
      }
    }
  }



}


