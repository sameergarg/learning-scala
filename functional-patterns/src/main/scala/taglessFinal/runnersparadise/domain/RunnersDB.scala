package taglessFinal.runnersparadise.domain

import taglessFinal.runnersparadise.domain.Model.Race.{Race, RaceId}
import taglessFinal.runnersparadise.domain.Model.Registration.Reg
import taglessFinal.runnersparadise.domain.Model.Runner.{Name, Runner, RunnerId}

import scala.collection.mutable.ArrayBuffer

object RunnersDB {
  //TODO use state monad RUNNER-DB

  val sam = Runner(RunnerId(1), Name("Sam"))

  val runners = ArrayBuffer(sam)

  val marathon = Race(RaceId(1), taglessFinal.runnersparadise.domain.Model.Race.Name("Marathon"), 10)

  val races = ArrayBuffer(marathon)

  val registrations: scala.collection.mutable.Map[RaceId, Reg] = collection.mutable.Map() ++ races.map(race => race.id -> Reg(races.head, runners.to[Set])).toMap


}
