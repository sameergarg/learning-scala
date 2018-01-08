package taglessFinal.runnersparadise

import cats.Monad
import cats.data.{EitherT, OptionT}
import cats.implicits._
import taglessFinal.runnersparadise.Algebra.{RaceAlgebra, RegistrationAlgebra, RunnerAlgebra}
import taglessFinal.runnersparadise.domain.Model.Race.{Name, Race, RaceId}
import taglessFinal.runnersparadise.domain.Model.Registration.Reg
import taglessFinal.runnersparadise.domain.Model.Runner.{Runner, RunnerId}
import taglessFinal.runnersparadise.domain.Model.{Race, Runner, RunnersParadiseError}
import taglessFinal.runnersparadise.domain.Model.RunnersParadiseError.{RaceDoesNotExist, RegistrationError, RunnerDoesNotExist}

import scala.language.higherKinds


object Program {

  class RegisterRunnerForRace[F[_] : Monad : RunnerAlgebra : RaceAlgebra : RegistrationAlgebra]() {

    def registerForRace(runnerId: RunnerId, raceId: RaceId): F[Option[Reg]] = (for {
      runner <- OptionT(RunnerAlgebra[F].findRunner(runnerId))
      race   <- OptionT(RaceAlgebra[F].findRace(raceId))
      reg    <- OptionT(RegistrationAlgebra[F].findReg(raceId)).orElse(OptionT.pure[F](Reg(race, Set.empty)))
      _      <- OptionT.pure[F](RegistrationAlgebra[F].saveReg(reg.add(runner)))
    } yield reg).value

    def registerForRaceWithErrorLogging(runnerId: RunnerId, raceId: RaceId): F[Either[RunnersParadiseError, Reg]] = {
      (for {
        runner <- OptionT(RunnerAlgebra[F].findRunner(runnerId)).toRight(RunnerDoesNotExist(runnerId))
        race <- OptionT(RaceAlgebra[F].findRace(raceId)).toRight(RaceDoesNotExist(raceId))
        reg <- OptionT(RegistrationAlgebra[F].findReg(raceId)).orElse(OptionT.pure[F](Reg(race, Set.empty))).toRight(RegistrationError)
        newReg <- OptionT.pure[F](reg.add(runner)).toRight(RegistrationError)
        _ <- OptionT.liftF(RegistrationAlgebra[F].saveReg(reg)).toRight(RegistrationError: RunnersParadiseError)
      } yield newReg).value

    }

  }
}
