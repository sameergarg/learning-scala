package excercise

import excercise.Exercises._

import scala.collection.immutable
import scala.concurrent.Future
import scala.util.Try

object WithoutCats extends Exercises {

  override def mergeMaps(map1: Map[String, List[String]], map2: Map[String, List[String]]): Map[String, List[String]] =
    map2.foldLeft(map1)((acc, next) => acc + (next._1 -> map1.get(next._1).fold(next._2)(_ ::: next._2)))

  override def varadicMapMerge(maps: Map[String, List[String]]*): Map[String, List[String]] = maps.foldLeft(Map.empty[String, List[String]])(mergeMaps(_, _))

  override def mapNested(nested: List[Try[Int]]): List[Try[Int]] = nested.map(_.map(_+1))

  override def validateEven(input: List[Int])(f: Int => Either[String, Int]): Either[String, List[Int]] =
    input.find(isEven(_).isLeft).fold[Either[String, List[Int]]](Right(input))(i => Left(s"$i is not even"))

  override def validateAndCreatePerson(name: String, age: Int): Either[Vector[String], Person] = (validatedName(name), validatedAge(age)) match {
    case (Right(n), Right(a)) => Right(Person(n, a))
    case (n, a) => Left( (Vector(n, a) collect {
      case Left(v) => v
    }).flatten)
  }

  override def process(lines: List[String]): (Int, Int, Map[String, Int]) = {
    val res: immutable.Seq[(String, Int)] = for {
      line <- lines
      word <- line.split(" ")
    } yield (word, 1)

    res.foldLeft((0, 0, Map.empty[String, Int].withDefaultValue(0))){
      case ((words, characters, wordToCount), (word, count)) => (words + 1, characters + word.length, wordToCount.updated(word, wordToCount(word) + count))
    }
  }

  override def fetchMembershipInfo(id: Int): Future[Option[Exercises.MembershipInfo]] = getUser(id) match {
    case Some(user) => getMembershipInfo(user)
    case _ => Future.successful(None)
  }
}
