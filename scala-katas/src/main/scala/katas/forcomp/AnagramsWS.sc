import katas.forcomp.Anagrams.Occurrences
object AnagramsWS {
  def combinations(occurrences: Occurrences): List[Occurrences] = {

    for {
      (char, occurrence) <- occurrences
      times <- 1 to occurrence
      rest <- combinations(occurrences.filter(pair => pair._1 > char))
    } yield List((char, times)) ++ rest
  }
  ("HelloWorld" groupBy (x => x.toLower) mapValues (_.length) toList) sortWith (_._1 < _._1)
  val chars = combinations(List(('a', 2), ('b', 2)))
  def subtract(x: Occurrences, y: Occurrences): Occurrences = {
    x.map{xs => (xs._1, xs._2 - y.find(_._1==xs._1).getOrElse(('2',0))._2)}.filter(xs=>xs._2>0)
  }
  val lard = List(('a', 1), ('d', 1), ('l', 1), ('r', 2))
  val r = List(('r', 1),('a',1))
  subtract(lard,r)
}