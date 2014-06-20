import katas.streams.bloxorzgame.{StringParserTerrain}
object test {
  val levelVector = Vector(Vector('-', '*', '*', 'S'), Vector('-','*', '*','-'), Vector('*', '*', 'T', '-'))

  object StringParserTerrainObject extends StringParserTerrain {
    override val level: String =
"""------
  |--ST--
  |--oo--
  |--oo--
  |------""".stripMargin
  }
  import StringParserTerrainObject._
  StringParserTerrainObject.findChar('S', levelVector)
  val tf = terrainFunction(levelVector)
  tf(Pos(0, 0))
  tf(Pos(0, 1))
  tf(Pos(0, 2))
  tf(Pos(0, 3))
  tf(Pos(1, 0))
  tf(Pos(1, 1))
  tf(Pos(1, 2))
  tf(Pos(1, 3))
  tf(Pos(2, 0))
  tf(Pos(2, 1))
  tf(Pos(2, 2))
  tf(Pos(2, 3))
  startPos
  goal
}

