package taglessFinal.runnersparadise.interpreter

import cats.Id
import taglessFinal.runnersparadise.Algebra.CombinedAlgebra
import taglessFinal.runnersparadise.domain.Model.Race.{Race, RaceId}
import taglessFinal.runnersparadise.domain.Model.Registration.Reg
import taglessFinal.runnersparadise.domain.Model.Runner.{Name, Runner, RunnerId}

import scala.collection.mutable.ArrayBuffer

object IdBasedInterpreter {
  val sam = Runner(RunnerId(1), Name("Sam"))

  val runners = ArrayBuffer(sam)

  val marathon = Race(RaceId(1), taglessFinal.runnersparadise.domain.Model.Race.Name("Marathon"), 10)

  val races = ArrayBuffer(marathon)

  val registrations: scala.collection.mutable.Map[RaceId, Reg] = collection.mutable.Map() ++ races.map(race => race.id -> Reg(races.head, runners.to[Set])).toMap

  implicit val idBasedInterpreter = new CombinedAlgebra[Id] {

    override def findRunner(id: RunnerId) = runners.find(id == _.id)

    override def saveRunner(runner: Runner) = runners += runner

    override def findRace(id: RaceId) = races.find(id == _.id)

    override def saveRace(race: Race) = races += race


    override def findReg(id: RaceId) = registrations.get(id)

    override def saveReg(reg: Reg) = registrations += reg.race.id -> reg
  }


}
