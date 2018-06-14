package scalacheck

import org.scalatest.WordSpec
import org.scalatest.prop.Checkers

import org.scalacheck.Gen
import org.scalacheck.Gen._
import org.scalacheck.Prop.forAll

class GeneratorSpec extends WordSpec with Checkers {

  "Generator" should {
    "create generator" in {
      val myGen = for {
        n <- Gen.choose(10, 20)
        m <- Gen.choose(2 * n, 500)
      } yield (n, m)

      check {
        forAll(myGen) {
          case (n, m) => (m >= 2 * n) == true

        }
      }
    }

    "pick oneOf generator" in {
      val vowel = Gen.oneOf('A', 'E', 'I', 'O', 'U')

      val validChars: Seq[Char] = 'A' to 'Z'

      check {
        forAll(vowel) { v =>
          validChars.contains(v)
        }
      }
    }

    "other generators" in {
      check {
        forAll(alphaChar)(_.isDigit == false
        )
      }

      check {
        forAll(posNum[Int])(n => (n > 0) == true
        )
      }

      check {
        forAll(listOfN(10, posNum[Int])) { list =>
          !list.exists(_ < 0) && list.length == 10

        }
      }
    }

    "conditional generator" in {
      val setOfEvens = Gen.choose(0, 2000) suchThat (_%2 ==0 )
      check(
        forAll(setOfEvens)(even => even < 2001  && even %2 == 0)
      )
    }

    "support case class" in {
      case class Foo(intValue: Int, charValue: Char)

      val fooGen = for {
        i <- Gen.posNum[Int]
        c <- Gen.alphaChar
      } yield Foo(i, c)

      check {
        forAll(fooGen) {foo =>
          foo.intValue > 0 && foo.charValue.isDigit == false
        }
      }

    }

    "sized generator" in {
      val myGen = Gen.sized { size =>
        val positiveNumbers = size / 3
        val negativeNumbers = size * 2 / 3
        for {
          posNumList <- Gen.listOfN(positiveNumbers, Gen.posNum[Int])
          negNumList <- Gen.listOfN(negativeNumbers, Gen.posNum[Int] map (n => -n))
        } yield (size, posNumList, negNumList)
      }

      check {
        forAll(myGen) {
          case (genSize, posN, negN) =>
            posN.length == genSize /3 && negN.length == genSize * 2/3
        }
      }
    }

    "container" in {
      val genStringStream = Gen.containerOf[Stream, String](Gen.alphaStr)

      val genBoolArray = Gen.containerOf[Array, Boolean](true)

      val genIntList: Gen[List[Int]] = Gen.containerOf[List, Int](Gen.oneOf(2, 4, 6))

      val validNumbers: List[Int] = 1 to 10 toList


        check {
          forAll(genIntList)(_ forall (elem => validNumbers.contains(elem)))
        }
    }
  }
}
