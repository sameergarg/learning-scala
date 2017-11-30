package taglessFinal.runnersparadise

import cats.Id
import org.scalatest.Matchers
import taglessFinal.runnersparadise.Algebra.RaceAlgebra.RaceId
import taglessFinal.runnersparadise.Algebra.RunnerAlgebra.RunnerId
import taglessFinal.runnersparadise.Interpreter._
import taglessFinal.runnersparadise.Program.RegisterRunnerForRace

class RegisterRunnerForRaceSpec extends org.scalatest.WordSpec with Matchers {

  "Program" should {
    "register runner for race synchronously" when {
      "race and runner exists" in {
        val program = new RegisterRunnerForRace[Id]()
        program.registerForRace(sam.id, marathon.id) should not be empty
      }
    }
    "not register runner for race" when {
      "race does not exist" in {
        val program = new RegisterRunnerForRace[Id]()
        program.registerForRace(sam.id, RaceId(-1)) shouldBe None
      }

      "runner does not exists" in {
        val program = new RegisterRunnerForRace[Id]()
        program.registerForRace(RunnerId(-1), marathon.id) shouldBe None
      }
    }
  }

}
