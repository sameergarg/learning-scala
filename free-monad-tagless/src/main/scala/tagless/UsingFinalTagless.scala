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

  //interpreter
  val futureUserServiceInterpreter = new UserRepositoryAlg[Future] {
    override def findUser(userId: UserId) = userId match {
      case even if (isEven(even.id)) => Future.successful(Some(User(even, Name("even"))))
      case odd if (isOdd(odd.id)) => Future.successful(None)
    }

    override def updateUser(user: User) = user.userId match {
      case even if (isEven(even.id)) => Future.successful()
      case odd if (isOdd(odd.id)) => Future.successful(None)
    }
  }

  //Email service
  trait EmailServiceAlg[F[_]] {
    def sendEmail(emailId: String, subject: String, body: String): Future[Unit]
  }

  val futureEmailServiceInterpreter = new EmailServiceAlg[Future] {
    override def sendEmail(emailId: String, subject: String, body: String): Future[Unit] = {
      println(s"Sending email to $emailId with subject: $subject and body: $body")
      Future.successful(())
    }
  }

  //program using algebra
  class ChangeUserService[F[_] : Monad](userRepository: UserRepositoryAlg[F], emailService: EmailServiceAlg[F]) {
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


  //test
  for (i <- 1 to 10) {
    val result: Future[Either[String, Unit]] = new ChangeUserService(futureUserServiceInterpreter, futureEmailServiceInterpreter).changeName(Name("test"), UserId(i))
    println(s"userId: $i => ${Await.result(result, Duration.Inf)}")

  }

}
