package katas.ninetynineproblems

import org.scalatest.{Matchers, WordSpec}
import katas.ninetynineproblems.ArithmeticProblems._
/**
  *
  */
class ArithmeticProblemsSpec extends WordSpec with Matchers {
  "In Arithmetic problems" should {
    "detect prime" in {
      isPrime(1) shouldBe false
      isPrime(2) shouldBe true
      isPrime(3) shouldBe true
      isPrime(4) shouldBe false
      isPrime(5) shouldBe true
      isPrime(10) shouldBe false
      isPrime(17) shouldBe true
    }
  }
}
