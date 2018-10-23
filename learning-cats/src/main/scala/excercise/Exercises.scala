package excercise

import excercise.Exercises.{MembershipInfo, Person}

import scala.concurrent.Future
import scala.util.Try

/**
  * http://www.geekabyte.io/2018/09/easing-into-cats-and-case-for-category.html
  */

trait Mappy[F[_]] {
  def map[A,B](a: F[A])(f: A => B): F[B]
}

object Mappy {

  implicit val optionMappy = new Mappy[Option] {
    override def map[A, B](a: Option[A])(f: A => B): Option[B] = a.map(f)
  }

  implicit val tryMappy = new Mappy[Try] {
    override def map[A, B](a: Try[A])(f: A => B): Try[B] = a.map(f)
  }

  type EitherInt[A] = Either[Int, A]
  implicit val eitherMapper = new Mappy[EitherInt] {
    override def map[A, B](a: EitherInt[A])(f: A => B): EitherInt[B] = a.map(f)
  }

  def genericMap[F[_] : Mappy, A, B](fa: F[A])(f: A ⇒ B) = implicitly[Mappy[F]].map(fa)(f)
}

trait Exercises {
  def mergeMaps(map1: Map[String, List[String]], map2: Map[String, List[String]]): Map[String, List[String]]

  def varadicMapMerge(maps: Map[String, List[String]]*): Map[String, List[String]]

  def mapNested(nested: List[Try[Int]]): List[Try[Int]]

  // validation function
  def isEven(value: Int): Either[String, Int] = {
    if (value % 2 == 0) {
      Right(value)
    } else {
      Left(s"$value is not even")
    }
  }

  def validateEven(input: List[Int])(f: Int => Either[String, Int]): Either[String, List[Int]]


  //construct a Person object, but only if all properties required are valid. If any or all of the properties are not valid, then report the validation failure
  def validateAndCreatePerson(name: String, age: Int): Either[Vector[String], Person]

  def validatedName(name: String) = Either.cond(name.forall(!_.isDigit), name, Vector("Name cannot contain numbers"))
  def validatedAge(age: Int) = Either.cond(age > 0, age, Vector("Age cannot be zero or less than zero"))

  def process(lines: List[String]) : (Int, Int, Map[String, Int])

  //Chaining asynchronous calls that can return or not return values
  def fetchMembershipInfo(id: Int): Future[Option[MembershipInfo]]

}

object Exercises {
  case class Person(name: String, age: Int)

  // domain objects representing users and membership information
  case class User(id:Int)
  case class MembershipInfo(mtype: String)

  // stub implementation for getUser that only returns a user when called with id of 1
  def getUser(id: Int): Option[User] = {
    if (id == 1) {
      Some(User(id))
    } else {
      None
    }
  }

  // stub implementation of getMembershipIfo that only returns membership info when called with a user
  // with id of 1
  def getMembershipInfo(user:User): Future[Option[MembershipInfo]] = {
    if (user.id == 1) {
      Future.successful(Some(MembershipInfo("Standard")))
    } else {
      Future.successful(None)
    }
  }

}
