package taglessFinal.runnersparadise

import taglessFinal.runnersparadise.Algebra.RaceAlgebra.{Race, RaceId}
import taglessFinal.runnersparadise.Algebra.RegistrationAlgebra.{Reg, RegId}
import taglessFinal.runnersparadise.Algebra.RunnerAlgebra.{Runner, RunnerId}

object Algebra {

  trait RunnerAlgebra[F[_]] {
    def findRunner(id: RunnerId): F[Option[Runner]]

    def saveRunner(runner: Runner): F[Unit]
  }

  object RunnerAlgebra {

    case class Runner(value: String) extends AnyVal

    case class RunnerId(value: Int) extends AnyVal

    def apply[F[_]: RunnerAlgebra]: RunnerAlgebra[F] = implicitly

  }

  trait RaceAlgebra[F[_]] {
    def findRace(id: RaceId): F[Option[Race]]

    def saveRace(race: Race): F[Unit]
  }

  object RaceAlgebra {

    def apply[F[_]: RaceAlgebra]: RaceAlgebra[F] = implicitly

    case class RaceId(value: Int) extends AnyVal

    case class Race(value: String) extends AnyVal

  }

  trait RegistrationAlgebra[F[_]] {
    def findRace(id: RegId): F[Option[Reg]]

    def saveRace(reg: Reg): F[Unit]
  }

  object RegistrationAlgebra {

    def apply[F[_]: RegistrationAlgebra]: RegistrationAlgebra[F] = implicitly

    case class RegId(value: Int) extends AnyVal

    case class Reg(value: String) extends AnyVal

  }

  sealed trait Error extends Exception

  object Error {
    case object RegistrationError extends Error
  }

}
