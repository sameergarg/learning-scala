package taglessFinal.runnersparadise

import org.scalatest.Matchers
import taglessFinal.runnersparadise.Interpreter.StateBased._
import taglessFinal.runnersparadise.Program.RegisterRunnerForRace
import taglessFinal.runnersparadise.domain.Model.Race.{Race, RaceId}
import taglessFinal.runnersparadise.domain.Model.Registration.Reg
import taglessFinal.runnersparadise.domain.Model.Runner.{Name, Runner, RunnerId}
import taglessFinal.runnersparadise.domain.RunnersDB._

class StateBasedInterpreterSpec extends org.scalatest.WordSpec with Matchers {
  val interpreter = new RegisterRunnerForRace[DBState]()

  val sam = Runner(RunnerId(1), Name("Sam"))
  val runners = Set(sam)

  val marathon = Race(RaceId(1), taglessFinal.runnersparadise.domain.Model.Race.Name("Marathon"), 10)

  val races = Set(marathon)

  val registrations = Set(Reg(marathon, runners))


  val initDB: DB = DB(runners, races, registrations)

  "Program" should {
    "register runner for race synchronously" when {
      "race and runner exists" in {
        val reg = interpreter.registerForRace(sam.id, marathon.id).run(initDB).value
        reg._2 should not be empty
      }
    }

    "not register runner for race" when {
      "race does not exist" in {
        val registration = interpreter.registerForRace(sam.id, RaceId(-1)).run(initDB).value
        registration._2 shouldBe None
      }

      "runner does not exists" in {
        val registration = interpreter.registerForRace(RunnerId(-1), marathon.id).run(initDB).value
        registration._2 shouldBe None
      }
    }
  }

}
