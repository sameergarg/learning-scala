package katas.futures

import scala.concurrent.{Future, Promise}
import scala.util.{Success, Failure, Random, Try}
import scala.concurrent.ExecutionContext.Implicits.global

object SimplePromise extends App {

  def successfulPromise = {
    val promise = Promise[String]

    val future = promise.future

    promise.success("I have kept my promise")

    println(future.value)
  }

  def failedPromise = {
    val promise= Promise[String]

    val future = promise.future

    promise.failure(new UnsupportedOperationException("I failed to deliver"))

    println(future.value)
  }

  def completedPromise = {
    val p = Promise[String]
    val f = p.future
    Random.nextBoolean() match {
      case true => p.complete(Success("I completed successfully"))
      case false => p.complete(Failure{new UnsupportedOperationException("I failed to deliver")})
    }

    println(f.value)
  }

  def completeWithPromise = {
    val p = Promise[String]
    val f = p.future
    p.completeWith(Future("I completed successfully"))
    println(f.value)
  }

  println("*** Successful Promise ***")
  successfulPromise
  println("*** Failed Promise ***")
  failedPromise
  println("*** Completed Promise with Success/Failure ***")
  completedPromise
  println("*** Completed With Promise with Success/Failure ***")
  completeWithPromise

}
