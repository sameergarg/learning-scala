package functionalproginscala.ch07purelyfunctionalparallelism

import org.scalatest.{Matchers, WordSpec}
import ParallelComputation._
/**
 * Created by sameer on 07/01/15.
 */
class ParallelComputationSpec extends WordSpec with Matchers {

  "Sequence of ints" when {
    "summed recursively" should {
      "sum the sequence" in {
        sumsRecursively(Vector(1,2,3,4)) shouldBe 10
      }
    }
  }

}
