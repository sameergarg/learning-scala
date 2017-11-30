package taglessFinal.runnersparadise

import taglessFinal.runnersparadise.Algebra.RaceAlgebra.{Race, RaceId}
import taglessFinal.runnersparadise.Algebra.RegistrationAlgebra.{Reg}
import taglessFinal.runnersparadise.Algebra.RunnerAlgebra.{Runner, RunnerId}

object Algebra {

  trait RunnerAlgebra[F[_]] {
    def findRunner(id: RunnerId): F[Option[Runner]]

    def saveRunner(runner: Runner): F[Unit]
  }

  object RunnerAlgebra {

    final case class RunnerId(value: Int) extends AnyVal

    final case class Name(value: String) extends AnyVal

    case class Runner(id: RunnerId, name: Name)

    def apply[F[_]: RunnerAlgebra]: RunnerAlgebra[F] = implicitly

  }

  trait RaceAlgebra[F[_]] {
    def findRace(id: RaceId): F[Option[Race]]

    def saveRace(race: Race): F[Unit]
  }

  object RaceAlgebra {

    def apply[F[_]: RaceAlgebra]: RaceAlgebra[F] = implicitly

    final case class RaceId(value: Int) extends AnyVal

    final case class Name(value: String) extends AnyVal

    case class Race(id: RaceId, name: Name, maxParticipants: Int)

  }

  trait RegistrationAlgebra[F[_]] {
    def findReg(id: RaceId): F[Option[Reg]]

    def saveReg(reg: Reg): F[Unit]
  }

  object RegistrationAlgebra {

    def apply[F[_]: RegistrationAlgebra]: RegistrationAlgebra[F] = implicitly

    case class Reg(race: Race, runners: Set[Runner]) {
      def add(runner: Runner) = this.copy(runners = runners + runner)
    }

  }

  sealed trait Error extends Exception

  object Error {
    case object RegistrationError extends Error
  }

  trait CombinedAlgebra[F[_]] extends RunnerAlgebra[F] with RaceAlgebra[F] with RegistrationAlgebra[F]

}
