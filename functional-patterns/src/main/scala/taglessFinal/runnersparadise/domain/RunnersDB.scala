package taglessFinal.runnersparadise.domain

import taglessFinal.runnersparadise.domain.Model.Race.{Race, RaceId}
import taglessFinal.runnersparadise.domain.Model.Registration.Reg
import taglessFinal.runnersparadise.domain.Model.Runner.Runner

object RunnersDB {
  case class DB(runners: Set[Runner], races: Set[Race], registrations: Set[Reg]) {
    def registrationsFor(raceId: RaceId): Option[Reg] = registrations.find(reg => reg.race.id == raceId)
  }
}
