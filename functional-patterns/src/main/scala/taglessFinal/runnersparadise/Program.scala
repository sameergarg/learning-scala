package taglessFinal.runnersparadise

import cats.Monad
import cats.data.OptionT
import taglessFinal.runnersparadise.Algebra.RaceAlgebra.RaceId
import taglessFinal.runnersparadise.Algebra.RegistrationAlgebra._
import taglessFinal.runnersparadise.Algebra.RunnerAlgebra._
import taglessFinal.runnersparadise.Algebra.{RaceAlgebra, RegistrationAlgebra, RunnerAlgebra}
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
