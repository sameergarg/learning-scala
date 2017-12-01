package taglessFinal.runnersparadise

import taglessFinal.runnersparadise.domain.Model.Race.{Race, RaceId}
import taglessFinal.runnersparadise.domain.Model.Registration.Reg
import taglessFinal.runnersparadise.domain.Model.Runner.{Runner, RunnerId}

object Algebra {

  trait RunnerAlgebra[F[_]] {
    def findRunner(id: RunnerId): F[Option[Runner]]
    def saveRunner(runner: Runner): F[Unit]
  }

  object RunnerAlgebra {
    def apply[F[_] : RunnerAlgebra]: RunnerAlgebra[F] = implicitly
  }

  trait RaceAlgebra[F[_]] {
    def findRace(id: RaceId): F[Option[Race]]
    def saveRace(race: Race): F[Unit]
  }

  object RaceAlgebra {
    def apply[F[_] : RaceAlgebra]: RaceAlgebra[F] = implicitly
  }

  trait RegistrationAlgebra[F[_]] {
    def findReg(id: RaceId): F[Option[Reg]]
    def saveReg(reg: Reg): F[Unit]
  }

  object RegistrationAlgebra {
    def apply[F[_] : RegistrationAlgebra]: RegistrationAlgebra[F] = implicitly
  }

  trait CombinedAlgebra[F[_]] extends RunnerAlgebra[F] with RaceAlgebra[F] with RegistrationAlgebra[F]
}
