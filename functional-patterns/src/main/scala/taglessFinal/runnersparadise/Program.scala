package taglessFinal.runnersparadise

import cats.Monad
import cats.implicits._
import taglessFinal.runnersparadise.Algebra.RaceAlgebra.RaceId
import taglessFinal.runnersparadise.Algebra.RegistrationAlgebra._
import taglessFinal.runnersparadise.Algebra.RunnerAlgebra._
import taglessFinal.runnersparadise.Algebra.{Error, RaceAlgebra, RegistrationAlgebra, RunnerAlgebra}

object Program {

  class RegisterRunnerForRace[F[_]: Monad : RunnerAlgebra: RaceAlgebra: RegistrationAlgebra] {

    def registerForRace(runnerId: RunnerId, raceId: RaceId): F[Either[Error, Reg]] =
      for {
        runner <- RunnerAlgebra[F].findRunner(runnerId)
      } yield {???}
  }
}
