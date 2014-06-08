import katas.forcomp.Anagrams.Occurrences
object AnagramsWS {


  def combinations(occurrences: Occurrences) = {
    {
      for {
        occurrence <- occurrences
        repeat <- 1 to occurrence._2
      } yield occurrence._1
    } mkString
  }

  val chars = combinations(List(('a', 2), ('b', 2)))

  {for{
    //char <- chars
    length <- 1 to chars.length
  } yield chars.combinations(length) toList}

}