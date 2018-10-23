package excercise

import cats._
import cats.data._
import cats.implicits._
import excercise.Exercises._

import scala.concurrent.Future
import scala.util.Try
import scala.concurrent.ExecutionContext.Implicits.global

object WithCats extends Exercises {
  override def mergeMaps(map1: Map[String, List[String]], map2: Map[String, List[String]]): Map[String, List[String]] = map1 |+| map2

  override def varadicMapMerge(maps: Map[String, List[String]]*): Map[String, List[String]] = maps.toList.combineAll

  override def mapNested(nested: List[Try[Int]]): List[Try[Int]] = Nested(nested).map(_ + 1).value

  override def validateEven(input: List[Int])(f: Int => Either[String, Int]): Either[String, List[Int]] =
    input.traverse(isEven)

  override def validateAndCreatePerson(name: String, age: Int): Either[Vector[String], Person] =
    (Validated.fromEither(validatedName(name)), Validated.fromEither(validatedAge(age))).mapN(Person).toEither

  override def process(lines: List[String]): (Int, Int, Map[String, Int]) = (for {
    line <- lines
    word <- line.split(" ")
  } yield (1, word.length, Map(word -> 1))).combineAll

  override def fetchMembershipInfo(id: Int): Future[Option[Exercises.MembershipInfo]] = getUser(id).flatTraverse(getMembershipInfo(_))
}
