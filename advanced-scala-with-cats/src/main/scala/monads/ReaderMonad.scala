package monads

import cats.Id
import cats.data.{Kleisli, Reader}

object ReaderMonad extends App {

  case class User(name: String)
  case object User {
    val foo = User("foo")
    val bar = User("bar")
  }

  class AuthService(userRepo: UserRepo) {
    def isLogged(user: User): Boolean = userRepo.findUser(user.name).isDefined
  }

  class GreetService(userRepo: UserRepo) {
    def greet(user: User, isLoggedIn: Boolean): String = if(isLoggedIn) s"Hello ${user.name}" else "Hello there"
  }

  trait UserRepo {
    def allUsers(): List[User]

    def findUser(name: String): Option[User]
  }

  object InMemoryUserRepo extends UserRepo {
    import User._

    val users = List(foo, bar)

    override def findUser(name: String): Option[User] = users.find(_.name == name)

    override def allUsers(): List[User] = users
  }


  case class Environment(authService: AuthService, greetService: GreetService)

  object Environment {
    def isLogged(user: User): Reader[Environment, Boolean] = Reader(env => env.authService.isLogged(user))

    def greet(user: User, isLoggedIn: Boolean): Reader[Environment, String] = Reader(env => env.greetService.greet(user, isLoggedIn))
  }


  val environment = Environment(new AuthService(InMemoryUserRepo) , new GreetService(InMemoryUserRepo))

  import User._

  val program: Kleisli[Id, Environment, String] = for{
    isLoggedIn <- Environment.isLogged(foo)
    greeting <- Environment.greet(foo, isLoggedIn)
  } yield greeting

  println(
    program.run(environment)
  )

  val programforUser = (user: User) => for{
    isLoggedIn <- Environment.isLogged(user)
    greeting <- Environment.greet(user, isLoggedIn)
  } yield greeting

  println(
    programforUser(User("baz")).run(environment)
  )

}
