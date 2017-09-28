package freemonad

import cats.free.Free
import cats.implicits._
import model.UserADT._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

//https://softwaremill.com/free-tagless-compared-how-not-to-commit-to-monad-too-early/
object UsingFreeMonad extends App {

  //Define Algebra/Instructions
  sealed trait UserRepositoryAlg[T]

  //describe the external interactions as data using a family of case classes
  case class FindUser(id: UserId) extends UserRepositoryAlg[Option[User]]

  case class UpdateUser(user: User) extends UserRepositoryAlg[Unit]

  //Define Monad
  type UserRepository[T] = Free[UserRepositoryAlg, T]

  //helper methods which lift "bare" instructions into the free monad context
  class UserRepoOps {
    def findUser(id: UserId): UserRepository[Option[User]] = Free.liftF(FindUser(id))

    def updateUser(user: User): UserRepository[Unit] = Free.liftF(UpdateUser(user))
  }

  //program/instructions to define business logic that use Free monad and no concrete Monad
  class UserService(userRepo: UserRepoOps) {
    def changeName(name: Name, id: UserId): UserRepository[Either[String, Unit]] = {
      userRepo.findUser(id).flatMap {
        case None => Free.pure(Left("No such user"))
        case Some(user) =>
          val changedUser = user.copy(name = name)
          userRepo.updateUser(changedUser).map(_ => Right(()))
      }
    }
  }

  import cats.~>

  //interpreter of the program using concrete Monad
  val futureInterpreter = new (UserRepositoryAlg ~> Future) {
    override def apply[A](fa: UserRepositoryAlg[A]): Future[A] = fa match {
      case FindUser(id) => id match {
        case even if (isEven(even.id)) => Future.successful(Some(User(even, Name("even"))))
        case odd if (isOdd(odd.id)) => Future.successful(None)

      }
      case UpdateUser(user) =>  user.userId match {
        case even if (isEven(even.id)) => Future.successful(())
        case odd if (isOdd(odd.id)) => Future.successful(())
      }
    }
  }

  for (i <- 1 to 10) {
    val result: Future[Either[String, Unit]] = new UserService(new UserRepoOps).changeName(Name("test"), UserId(i)).foldMap(futureInterpreter)
    println(s"userId: $i => ${Await.result(result, Duration.Inf)}")

  }


}

