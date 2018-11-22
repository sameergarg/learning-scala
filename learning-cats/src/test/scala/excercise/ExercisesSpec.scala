package excercise

import excercise.Exercises.{MembershipInfo, Person}
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{Matchers, WordSpec}

import scala.util.{Failure, Success}

class ExercisesSpec extends WordSpec with Matchers with ScalaFutures {

  val testClasses = Seq(WithoutCats, WithCats)

  testClasses.foreach { t =>
    s"Excercise functions in  ${t.getClass.getName}" should {
      "merge maps" in {
        val movies1 = Map(
          "Will Smith" -> List("Wild Wild West", "Bad Boys", "Hitch"),
          "Jada Pinkett" -> List("Woo", "Ali", "Gotham")
        )

        val movies2 = Map(
          "Will Smith" -> List("Made in America"),
          "Angelina Jolie" -> List("Foxfire", "The Bone Collector", "Original Sin")
        )

        t.mergeMaps(movies1, movies2) shouldBe
          Map("Will Smith" -> List("Wild Wild West", "Bad Boys", "Hitch", "Made in America"), "Jada Pinkett" -> List("Woo", "Ali", "Gotham"), "Angelina Jolie" -> List("Foxfire", "The Bone Collector", "Original Sin"))

        t.varadicMapMerge(Seq(movies1, movies2):_*) shouldBe Map("Will Smith" -> List("Wild Wild West", "Bad Boys", "Hitch", "Made in America"), "Jada Pinkett" -> List("Woo", "Ali", "Gotham"), "Angelina Jolie" -> List("Foxfire", "The Bone Collector", "Original Sin"))

      }

      s"map nested" in {
        t.mapNested(List (
          Success(1),
          Success(2),
          Failure(new Throwable("Not valid")),
          Success(3),
        )).toString() shouldBe List (
          Success(2),
          Success(3),
          Failure(new Throwable("Not valid")),
          Success(4)).toString()
      }

      "validate list successfully" in {
        val listOfAllEven: List[Int] = 2 to 8 by 2 toList
        val value: Either[String, List[Int]] = t.validateEven(listOfAllEven)(t.isEven)

        value shouldBe Right(List(2,4,6,8))
      }

      "fail fast in validation" in {
        val listOfInts: List[Int] = (1 to 10).toList
        t.validateEven(listOfInts)(t.isEven) shouldBe Left("1 is not even")
      }

      "fail slow in validation" in {
        List().
        t.validateAndCreatePerson("John", 20) shouldBe Right(Person("John",20))
        t.validateAndCreatePerson("John1", 20) shouldBe Left(Vector("Name cannot contain numbers"))
        t.validateAndCreatePerson("John", -1) shouldBe Left(Vector("Age cannot be zero or less than zero"))
        t.validateAndCreatePerson("John1", -1) shouldBe Left(Vector("Name cannot contain numbers", "Age cannot be zero or less than zero"))
      }

      "count words and characters" in {
        val lines: List[String] = List(
          "A bird in the hand is worth two in the bush",
          "Left hand doesn't know what the right hand is doing",
          "Like a moth to a flame"
        )

        t.process(lines) shouldBe  (27, 92, Map("Like" -> 1, "in" -> 2, "is" -> 2, "what" -> 1, "hand" -> 3, "bird" -> 1, "two" -> 1, "A" -> 1, "a" -> 2, "moth" -> 1, "to" -> 1, "know" -> 1, "doing" -> 1, "worth" -> 1, "Left" -> 1, "flame" -> 1, "doesn't" -> 1, "bush" -> 1, "right" -> 1, "the" -> 3))
      }

      "A user" when {
        "exists" should {
          "get membership info" in {
            whenReady(t.fetchMembershipInfo(1)) { result =>
              result shouldBe Some(MembershipInfo("Standard"))
            }
          }
        }

        "does not exist" should {
          "not get membership info" in {
            whenReady(t.fetchMembershipInfo(2)) { result =>
              result shouldBe None
            }
          }
        }
      }



    }
  }

  "generic mappy" should {
    "map options" in {
      import Mappy._
      Mappy.genericMap(Option(2))(_ + 1) shouldBe Some(3)
    }
  }

}
