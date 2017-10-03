package tagless

import cats.Monad
import cats.implicits._
import model.UserADT._
import scala.concurrent.{Await, Future}
import scala.concurrent.duration.Duration
import scala.concurrent.ExecutionContext.Implicits.global

object UsingFinalTagless extends App {

  //algebra/DSL
  trait UserRepositoryAlg[F[_]] {
    def findUser(userId: UserId): F[Option[User]]

    def updateUser(user: User): F[Unit]
  }

  //program using algebra
  class UserService[F[_] : Monad](userRepository: UserRepositoryAlg[F]) {
    def changeName(name: Name, userId: UserId): F[Either[String, Unit]] =
      userRepository.findUser(userId).flatMap {
        case None =>
          implicitly[Monad[F]].pure(Left("No such user"))
        case Some(user) =>
          println("changed name")
          val changed = user.copy(name=name)
          userRepository.updateUser(changed).map(_ => Right())
      }
  }

  //interpreter
  val futureInterpreter = new UserRepositoryAlg[Future] {
    override def findUser(userId: UserId) = userId match {
      case even if (isEven(even.id)) => Future.successful(Some(User(even, Name("even"))))
      case odd if (isOdd(odd.id)) => Future.successful(None)
    }

    override def updateUser(user: User) = user.userId match {
      case even if (isEven(even.id)) => Future.successful()
      case odd if (isOdd(odd.id)) => Future.successful(None)
    }
  }

  //test
  for (i <- 1 to 10) {
    val result: Future[Either[String, Unit]] = new UserService(futureInterpreter).changeName(Name("test"), UserId(i))
    println(s"userId: $i => ${Await.result(result, Duration.Inf)}")

  }

}
