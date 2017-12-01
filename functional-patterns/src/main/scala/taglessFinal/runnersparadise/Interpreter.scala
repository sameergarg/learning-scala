package taglessFinal.runnersparadise

import cats.Id
import taglessFinal.runnersparadise.Algebra.{CombinedAlgebra, RaceAlgebra, RegistrationAlgebra, RunnerAlgebra}
import taglessFinal.runnersparadise.domain.Model.Race.{Race, RaceId}
import taglessFinal.runnersparadise.domain.Model.Registration.Reg
import taglessFinal.runnersparadise.domain.Model.Runner.{Name, Runner, RunnerId}
import taglessFinal.runnersparadise.domain.RunnersDB._

object Interpreter {

  implicit val idBasedInterpreter = new CombinedAlgebra[Id] {

    override def findRunner(id: RunnerId) = runners.find(id == _.id)

    override def saveRunner(runner: Runner) = runners += runner

    override def findRace(id: RaceId) = races.find(id == _.id)

    override def saveRace(race: Race) = races += race


    override def findReg(id: RaceId) = registrations.get(id)

    override def saveReg(reg: Reg) = registrations += reg.race.id -> reg
  }

}
