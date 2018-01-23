import java.lang.Thread.sleep

import scala.concurrent.{Await, Future}
import scala.util.{Failure, Success, Try}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

//based on Viktor Klang blog at http://viktorklang.com/blog/
val f = Future {
  sleep(500)
  100
}

def show(any: Number): Future[String] = Future.successful(any.toString)

def showAsNumber(any: String): Future[Int] = Future.fromTry(Try(any.toInt))

//using flatten

val flattened: Future[String] = f.map(show(_)).flatten

println(Await.result(flattened, 4.seconds))

//using zipwith

val zipped: Future[String] = f.zipWith(show(10))((num, str) => s"Number: $num String:$str")


println(Await.result(zipped, 4.seconds))

//using transform
val transformed = f.transform(_ => Try(f).map(_.toString))

println(Await.result(transformed, 2.seconds))

// write map using transform
def mapUsingTransform[A,B](f: Future[A])(mapper: A => B): Future[B] = f.transform(_.map(mapper))

val mappedUsingTransform = mapUsingTransform(f)((a: Any) => s"String: ${a.toString}")

println(Await.result(mappedUsingTransform, 2.seconds))

//write recover using transform
def recoverUsingTransform[A,B >: A](f: Future[A])(pf: PartialFunction[Throwable, B]): Future[B] =
  f.transform{_.recover(pf)}

val recovered: Int = Await.result(recoverUsingTransform(showAsNumber("fail")) {
  case t: NumberFormatException =>
    println("I failed")
    0
}, 2.seconds)

println(recovered)

//using transformWith
val usingTransformWith = f.transformWith(t => Future.fromTry(t))

println(Await.result(usingTransformWith, Duration.Inf))

//write flatmap using transformWith
def flatMapUsingTransformWith[A, B](f: Future[A])(fn : A => Future[B]): Future[B] =
  f.transformWith {
    case Success(a) => fn(a)
    case Failure(ex) => Future.failed(ex)
  }

val flatmapped = flatMapUsingTransformWith(show(1))(showAsNumber)

println(Await.result(flatmapped, Duration.Inf))

//write recoverWith using transformWith
def recoverWithUsingTransformWith[A, B >: A](f: Future[A])(pf: PartialFunction[Throwable, Future[B]]): Future[B] =
  f.transformWith{
    case Success(_) => f
    case Failure(ex) => pf.apply(ex)
  }

