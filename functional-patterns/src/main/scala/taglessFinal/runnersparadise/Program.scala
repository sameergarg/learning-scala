package taglessFinal.runnersparadise

import cats.Monad
import cats.data.OptionT
import taglessFinal.runnersparadise.Algebra.{RaceAlgebra, RegistrationAlgebra, RunnerAlgebra}
import taglessFinal.runnersparadise.domain.Model.Race.RaceId
import taglessFinal.runnersparadise.domain.Model.Registration.Reg
import taglessFinal.runnersparadise.domain.Model.Runner.RunnerId

import scala.language.higherKinds


object Program {

  class RegisterRunnerForRace[F[_] : Monad : RunnerAlgebra : RaceAlgebra : RegistrationAlgebra]() {

    def registerForRace(runnerId: RunnerId, raceId: RaceId): F[Option[Reg]] = {
      (for {
        runner <- OptionT(RunnerAlgebra[F].findRunner(runnerId))
        race   <- OptionT(RaceAlgebra[F].findRace(raceId))
        reg    <- OptionT(RegistrationAlgebra[F].findReg(raceId)).orElse(OptionT.pure[F](Reg(race, Set.empty)))
        _      <- OptionT.pure[F](RegistrationAlgebra[F].saveReg(reg.add(runner)))
      } yield reg).value
    }
  }
}
