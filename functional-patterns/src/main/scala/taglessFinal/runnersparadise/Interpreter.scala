package taglessFinal.runnersparadise

import cats.Id
import taglessFinal.runnersparadise.Algebra.RaceAlgebra.{Race, RaceId}
import taglessFinal.runnersparadise.Algebra.RegistrationAlgebra.Reg
import taglessFinal.runnersparadise.Algebra.{CombinedAlgebra, RaceAlgebra, RegistrationAlgebra, RunnerAlgebra}
import taglessFinal.runnersparadise.Algebra.RunnerAlgebra.{Name, Runner, RunnerId}

import scala.collection.mutable.ArrayBuffer

object Interpreter {

  //TODO use state monad RUNNER-DB

  val sam = Runner(RunnerId(1), Name("Sam"))

  val runners = ArrayBuffer(sam)

  val marathon = Race(RaceId(1), taglessFinal.runnersparadise.Algebra.RaceAlgebra.Name("Marathon"), 10)

  val races = ArrayBuffer(marathon)

  val registrations: scala.collection.mutable.Map[RaceId, Reg] = collection.mutable.Map() ++ races.map(race => race.id -> Reg(races.head, runners.to[Set])).toMap

  implicit val idBasedInterpreter = new CombinedAlgebra[Id] {

    override def findRunner(id: RunnerAlgebra.RunnerId) = runners.find(id == _.id)

    override def saveRunner(runner: RunnerAlgebra.Runner) = runners += runner

    override def findRace(id: RaceId) = races.find(id == _.id)

    override def saveRace(race: Race) = races += race


    override def findReg(id: RaceId) = registrations.get(id)

    override def saveReg(reg: RegistrationAlgebra.Reg) = registrations += reg.race.id -> reg
  }

}
