package implicits

import implicits.TypeBoundImplicit.Option1.{PersonCanGreet, greet1}
import implicits.TypeBoundImplicit.Option2.greet2
import implicits.TypeBoundImplicit.Option3.greet3
import implicits.TypeBoundImplicit.Option4.greet4

/**
  * http://www.cakesolutions.net/teamblogs/demystifying-implicits-and-typeclasses-in-scala
  */
object TypeBoundImplicit extends App {

  object Option1 {

    trait CanGreet[T] {
      def greet(t: T): String
    }

    case class Person(speak: String)

    //The idea of this typeclass is that we can provide evidence that a class(Person) satisfies an interface(CanGreet).
    //Instead of having class(Person) implement an interface directly
    object PersonCanGreet extends CanGreet[Person] {
      override def greet(t: Person): String = t.speak
    }

    def greet1[T](t : T, evidence: CanGreet[T]) = evidence.greet(t)
  }

  println(greet1(Option1.Person("hello option 1"), PersonCanGreet))

  //////////////////////////////////////////////////////////////////////////

  object Option2 {
    trait CanGreet[T] {
      def greet(t: T): String
    }

    case class Person(speak: String)

    //The idea of this typeclass is that we can provide evidence that a class(Person) satisfies an interface(CanGreet).
    //Instead of having class(Person) implement an interface directly
    implicit object PersonCanGreet extends CanGreet[Person] {
      override def greet(t: Person): String = t.speak
    }

    def greet2[T](t : T)(implicit evidence: CanGreet[T]) = evidence.greet(t)
  }
  println(greet2(Option2.Person("hello option 2")))

  ///////////////////////////////////////////////////////////////////////////
  object Option3 {
    trait CanGreet[T] {
      def greet(t: T): String
    }

    case class Person(speak: String)

    //The idea of this typeclass is that we can provide evidence that a class(Person) satisfies an interface(CanGreet).
    //Instead of having class(Person) implement an interface directly
    implicit object PersonCanGreet extends CanGreet[Person] {
      override def greet(t: Person): String = t.speak
    }

    def greet3[T: CanGreet](t: T) = implicitly[CanGreet[T]].greet(t)


  }
  println(greet3(Option3.Person("hello option 3")))

  ///////////////////////////////////////////////////////////////////////////
  object Option4 {
    trait CanGreet[T] {
      def greet(t: T): String
    }

    object CanGreet {
      def apply[T: CanGreet]: CanGreet[T] = implicitly
    }

    case class Person(speak: String)

    implicit object PersonCanGreet extends CanGreet[Person] {
      override def greet(t: Person): String = t.speak
    }

    //type bound
    def greet4[T: CanGreet](t: T) = CanGreet[T].greet(t)
  }

  println(greet4(Option4.Person("hello option 4")))

}

