package taglessFinal.runnersparadise.domain

import taglessFinal.runnersparadise.domain.Model.Race.{Race, RaceId}
import taglessFinal.runnersparadise.domain.Model.Runner.{Runner, RunnerId}

object Model {

  object Runner {

    final case class RunnerId(value: Int) extends AnyVal

    final case class Name(value: String) extends AnyVal

    case class Runner(id: RunnerId, name: Name)

  }

  object Race {

    final case class RaceId(value: Int) extends AnyVal

    final case class Name(value: String) extends AnyVal

    case class Race(id: RaceId, name: Name, maxParticipants: Int)

  }

  object Registration {

    case class Reg(race: Race, runners: Set[Runner]) {
      def add(runner: Runner): Reg = this.copy(runners = runners + runner)
    }

  }

  sealed trait RunnersParadiseError extends Exception

  object RunnersParadiseError {

    case object RegistrationError extends RunnersParadiseError
    case class RunnerDoesNotExist(id: RunnerId) extends RunnersParadiseError
    case class RaceDoesNotExist(id: RaceId) extends RunnersParadiseError

  }

}
