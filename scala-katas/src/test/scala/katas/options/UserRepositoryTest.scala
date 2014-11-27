package katas.options

import org.scalatest.{Matchers, WordSpec}

/**
 * Created by sameer on 27/11/14.
 */
class UserRepositoryTest extends WordSpec with Matchers {

  "User repository" when {
    "asked for existing user" should {
      "have user" in {
        UserRepository.findUser(1).get.name shouldBe "foo"
      }
    }

    "asked for non existing user" should {
      "not have user" in {
        UserRepository.findUser(100) shouldBe None
      }
    }
  }

}
