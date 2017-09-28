package futures

import model.UserADT.{Name, User, UserId}

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import model.UserADT._

object UsingFuture extends App {

  trait UserRepository {
    def findUser(id: UserId): Future[Option[User]]

    def updateUser(user: User): Future[Unit]
  }

  class StubbedUserRepository extends UserRepository {

    override def findUser(userId: UserId): Future[Option[User]] = userId match {
      case even if (isEven(even.id)) => Future.successful(Some(User(even, Name("even"))))
      case odd if (isOdd(odd.id)) => Future.successful(None)
    }

    override def updateUser(user: User) = user.userId match {
      case even if (isEven(even.id)) => Future.successful()
      case odd if (isOdd(odd.id)) => Future.successful(None)
    }
  }

  class UserService(userRepository: UserRepository) {
    def changeName(userId: UserId, newName: Name): Future[Either[String, Unit]] =
      userRepository.findUser(userId).flatMap {
        case Some(existingUser) =>
          val changedUser: User = existingUser.copy(name = newName)
          userRepository.updateUser(changedUser).map(_ => Right(()))
        case None =>
          Future.successful(Left("No such user exists"))
      }
  }


  val us = new UserService(new StubbedUserRepository)

  for (i <- 1 to 10)
    println(s"userId: $i => ${Await.result(us.changeName(UserId(i), Name("fail")), Duration.Inf)}")
}