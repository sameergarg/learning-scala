package taglessFinal.runnersparadise

import cats.Id
import cats.data.State
import taglessFinal.runnersparadise.Algebra.{CombinedAlgebra, RaceAlgebra, RegistrationAlgebra, RunnerAlgebra}
import taglessFinal.runnersparadise.domain.Model.Race.{Race, RaceId}
import taglessFinal.runnersparadise.domain.Model.Registration.Reg
import taglessFinal.runnersparadise.domain.Model.Runner.{Name, Runner, RunnerId}
import taglessFinal.runnersparadise.domain.RunnersDB._

object Interpreter {

  type DBState[A] = State[DB, A]

  /*
  val myFunction: Int => Int => Int = ???


  def myFunc(x: Int, y: Int): Int = ???
*/
  // List
  // * -> *
  // For Boolean: Boolean -> List[Boolean]

  // State
  // * -> * -> *
  // For DB and Unit: DB -> Unit -> State[DB, Unit]

  implicit val stateBasedInterpreter = new CombinedAlgebra[DBState] {

    override def findRunner(id: RunnerId): DBState[Option[Runner]] = State(s => (s, s.runners.find(_.id == id)))

    override def saveRunner(runner: Runner): DBState[Unit] = State(s => (s.copy(runners = s.runners + runner), ()))

    override def findRace(id: RaceId): DBState[Option[Race]] = State(s => (s, s.races.find(_.id == id)))

    override def saveRace(race: Race): DBState[Unit] = State(s => (s.copy(races = s.races + race), ()))

    override def findReg(id: RaceId): DBState[Option[Reg]] = State(s => (s, s.registrations.find(_.race.id == id)))

    override def saveReg(reg: Reg): DBState[Unit] = State(s => (s.copy(registrations = s.registrations + reg), ()))
  }

}
