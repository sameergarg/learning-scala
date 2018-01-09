package taglessFinal.runnersparadise

import cats.Eval
import org.scalatest.Matchers
import taglessFinal.runnersparadise.interpreter.StateBasedInterpreter._
import taglessFinal.runnersparadise.Program.RegisterRunnerForRace
import taglessFinal.runnersparadise.domain.Model
import taglessFinal.runnersparadise.domain.Model.Race.{Race, RaceId}
import taglessFinal.runnersparadise.domain.Model.Registration.Reg
import taglessFinal.runnersparadise.domain.Model.Runner.{Name, Runner, RunnerId}
import taglessFinal.runnersparadise.domain.Model.RunnersParadiseError._
import taglessFinal.runnersparadise.domain.RunnersDB._

class StateBasedInterpreterSpec extends org.scalatest.WordSpec with Matchers {
  val interpreter = new RegisterRunnerForRace[DBState]()

  val defaultMaxParticipants = 10
  val sam = Runner(RunnerId(11), Name("Sam"))

  val runners = (0 to defaultMaxParticipants).foldLeft(Set(sam))((combined, id) => combined + Runner(RunnerId(id), Name(s"$id")) )

  val marathon = Race(RaceId(1), taglessFinal.runnersparadise.domain.Model.Race.Name("Marathon"), defaultMaxParticipants)

  val races = Set(marathon)

  val registrations = Set(Reg(marathon, runners))


  val initDB: DB = DB(runners, races, registrations)

  "Program" should {
    "register runner for race" when {
      "race and runner exists" in {
        val reg: (DB, Option[Reg]) = interpreter.registerForRace(sam.id, marathon.id).run(initDB).value
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

  "Program with error handling" should {
    "register runner for race" when {
      "race and runner exists" in {
        val reg: (DB, Either[Model.RunnersParadiseError, Reg]) = interpreter.registerForRaceWithErrorLogging(sam.id, marathon.id).run(initDB).value
        reg._2.isRight shouldBe true
        reg._2.exists(_.runners.exists(_ == sam)) shouldBe true
      }
    }

    "not register runner for race" when {
      "race does not exist" in {
        val nonExistentRaceId = RaceId(-1)
        val registration = interpreter.registerForRaceWithErrorLogging(sam.id, nonExistentRaceId).run(initDB).value
        registration._2 shouldBe Left(RaceDoesNotExist(nonExistentRaceId))
      }

      "runner does not exists" in {
        val nonExistentRunnerId = RunnerId(-1)
        val registration: (DB, Either[Model.RunnersParadiseError, Reg]) = interpreter.registerForRaceWithErrorLogging(nonExistentRunnerId, marathon.id).run(initDB).value
        registration._2 shouldBe Left(RunnerDoesNotExist(nonExistentRunnerId))
      }

      "number of runners exceed max participants" in {
        val filledToMax: DB = (0 to defaultMaxParticipants).foldLeft(initDB)((db, i) => interpreter.registerForRaceWithErrorLogging(RunnerId(i), marathon.id).run(db).value._1)

        val overfilled: (DB, Either[Model.RunnersParadiseError, Reg]) = interpreter.registerForRaceWithErrorLogging(sam.id, marathon.id).run(filledToMax).value
        overfilled._2.isLeft shouldBe true
        overfilled._2 shouldBe Left(RaceFullyBooked)
      }
    }
  }

}
