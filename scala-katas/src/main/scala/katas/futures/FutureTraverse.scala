package katas.futures

object FutureTraverse extends App {

  import concurrent._
  import ExecutionContext.Implicits.global
  import scala.concurrent.duration._

  val f = Future.traverse(Iterator(1,2,3))(i => Future{ i*i })

  val res = Await.result(f, 10.seconds)

  println(res.toList)


}
