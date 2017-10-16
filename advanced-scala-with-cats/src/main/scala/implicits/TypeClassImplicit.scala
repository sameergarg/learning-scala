package implicits

object TypeClassImplicit extends App {

  case class User(name: String, age: Int)
  /**
    * Allowing traits to be parameterised in this way gives us new options for adding functionality to classes
    * without modifying existing code,
    * @tparam C
    */
  //A type class is a trait with at least one type parameter
  trait CanChat[C] {
    def chat(c: C): String
  }

  //Now we can define this behaviour for existing classes like Int and String without modifying their code
  object CanChat {

    def apply[C: CanChat]: CanChat[C] = implicitly

    implicit object IntCanChat extends CanChat[Int] {
      override def chat(c: Int): String = c.toString
    }

    implicit object StringCanChat extends CanChat[String] {
      override def chat(c: String): String = c
    }

    //add chat behaviour on User class without modifying its code
    implicit object UserCanChat extends CanChat[User] {
      override def chat(user: User): String = s"I am ${user.name} and my age is ${user.age}"
    }


    object Syntax {
      implicit def chat[C: CanChat](c: C) = {
        CanChat[C].chat(c)
      }

      implicit class ChatOps[C: CanChat](underlying: C) {
        def :< = CanChat[C].chat(underlying)
      }
    }
  }

  import CanChat._
  import CanChat.Syntax._

  chat(User("sameer", 40))
  println(
    1 :<
  )
}
