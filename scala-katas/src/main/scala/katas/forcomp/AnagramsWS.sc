import katas.forcomp.Anagrams.Occurrences
object AnagramsWS {
  def combinations(occurrences: Occurrences): List[Occurrences] = {

    for {
      (char, occurrence) <- occurrences
      times <- 1 to occurrence
      rest <- combinations(occurrences.filter(pair => pair._1 > char))
    } yield List((char, times)) ++ rest
  }
  ("HelloWorld" groupBy (x => x.toLower) mapValues (_.length) toList) sortWith(_._1 < _._1)

  val chars = combinations(List(('a', 2), ('b', 2)))
}