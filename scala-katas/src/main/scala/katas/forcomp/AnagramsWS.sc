object AnagramsWS {
  val occurrences = List(('a', 2), ('b', 2))
  val elements = for {
    occurrence <- occurrences
    subsetOccurrence <- 1 to occurrence._2
  } yield (occurrence._1, subsetOccurrence)
  val subsets = elements.toSet.subsets.filter(_.size>0).map(_.toList).toList
  //val grouped = subsets.map{_.groupBy { y => y._1}}
  subsets.filter{filterDupes}

  def filterDupes: (List[(Char, Int)]) => Boolean = {
    listOfTuples => listOfTuples.groupBy { y => y._1}.mapValues(_.size).forall(_._2<=1)
  }
      //elements.map(convert)
  val aftergrouping= (List(('a',1), ('b',1), ('b',2))).groupBy { y => y._1}.mapValues(_.size).forall(_._2<=1)

  //.map(x=>x.toList).toList
  /*def convert(elements: Set[(Char, Int)]): List[(Char, Int)] = {
    elements.toSet.subsets.toList match {
      case isEmpty => List()
      case x: Set[(Char, Int)] => x.toList
    } //.map(x=>x.toList).toList
  }*/




}