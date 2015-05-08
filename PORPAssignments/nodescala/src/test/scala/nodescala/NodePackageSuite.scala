package nodescala

import org.scalatest.concurrent.PatienceConfiguration.Timeout
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{ShouldMatchers, FunSuite}

import scala.concurrent.{TimeoutException, Await, ExecutionContext, Future}
import ExecutionContext.Implicits.global
import scala.concurrent.duration._

/**
 * Created by sameer on 05/05/15.
 */
class NodePackageSuite extends FunSuite with ShouldMatchers with ScalaFutures {

  implicit val config = PatienceConfig(timeout = 2 seconds)

  test("all - should return failure when a future fails in List") {
    val listWithFailure:List[Future[String]] = List[Future[String]](Future.failed(new IllegalStateException()))
    ScalaFutures.whenReady(Future.all(listWithFailure).failed){ex => ex shouldBe a [IllegalStateException]}
  }


  test("all - should not return failure when all future success in List") {
    val listWithFailure:List[Future[String]] = List[Future[String]](Future.successful("I am success"))
    ScalaFutures.whenReady(Future.all(listWithFailure)){xs => xs should contain only("I am success")}
  }

  test("any - should return first successful completed future") {
    val delay1Future = Future {
      Thread.sleep(1000)
      "delay 1 sec"
    }
    val delay2Future = Future {
      Thread.sleep(2000)
      "delay 2 sec"
    }

    val delay3Future = Future {
      Thread.sleep(3000)
      "delay 3 sec"
    }

    val firstCompleted = Future.any(List(delay3Future, delay1Future, delay2Future))
    ScalaFutures.whenReady(firstCompleted, Timeout(1 seconds)){_ shouldBe "delay 1 sec"}

  }

  test("any - should return first failed completed future") {
    val delay1Future = Future {
      Thread.sleep(1000)
      "delay 1 sec"
    }
    val delay2Future = Future {
      Thread.sleep(2000)
      "delay 2 sec"
    }
    val noDelayFailure = Future {throw new IllegalStateException()}

    val firstCompleted = Future.any(List(noDelayFailure, delay1Future, delay2Future))
    ScalaFutures.whenReady(firstCompleted.failed){_ shouldBe a [IllegalStateException]}
  }

  test("delay - future is not completed before the delay") {
    //ScalaFutures.whenReady(Future.delay(2 seconds), Timeout(1 seconds)) { ex => ex shouldBe a[TimeoutException] }
  }
}
