package monads

import cats.data.{Kleisli, ReaderT}
import cats.implicits._
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global

object ReaderTMonad extends App {

  case class User(name: String)
  case object User {
    val foo = User("foo")
    val bar = User("bar")
  }

  trait UserRepo {
    def allUsers(): Future[List[User]]

    def findUser(name: String): Future[Option[User]]
  }

  object InMemoryUserRepo extends UserRepo {
    import User._

    val users = List(foo, bar)

    override def findUser(name: String): Future[Option[User]] = Future {users.find(_.name == name)}

    override def allUsers(): Future[List[User]] = Future { users }
  }

  class AuthService(userRepo: UserRepo) {
    def isLogged(user: User): Future[Boolean] = userRepo.findUser(user.name).map(_.isDefined)
  }

  class GreetService(userRepo: UserRepo) {
    def greet(user: User, isLoggedIn: Boolean): Future[String] = Future.successful(if(isLoggedIn) s"Hello ${user.name}" else "Hello there")
  }

  case class Environment(authService: AuthService, greetService: GreetService)

  object Environment {
    def isLogged(user: User): ReaderT[Future, Environment, Boolean] = ReaderT(env => env.authService.isLogged(user))

    def greet(user: User, isLoggedIn: Boolean): ReaderT[Future, Environment, String] = ReaderT(env => env.greetService.greet(user, isLoggedIn))
  }

  val environment = Environment(new AuthService(InMemoryUserRepo) , new GreetService(InMemoryUserRepo))

  import User._

  val programForUser: (User) => ReaderT[Future, Environment, String] = (user: User) => for{
    isLoggedIn <- Environment.isLogged(user)
    greeting <- Environment.greet(user, isLoggedIn)
  } yield greeting

  println(
    Await.result(programForUser(User("baz")).run(environment), Duration.Inf)
  )


  val program: Kleisli[Future, Environment, String] = programForUser(foo)

  println(
    Await.result(program.run(environment), Duration.Inf)
  )


}
