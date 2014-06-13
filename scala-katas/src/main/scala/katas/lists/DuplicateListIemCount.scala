package katas.lists

object DuplicateListIemCount {

  def countDuplicateCharactersUsingGroupBy(chars: List[Char]): List[(Char,Int)] = {
    chars.groupBy(x=>x).toList.map{case (char,listOfChars) => (char,listOfChars.length)}
  }

  def countDuplicateCharactersUsingGroupBy2(chars: List[Char]): List[(Char,Int)] = {
    chars groupBy identity mapValues(_.size) toList
  }

  def countDuplicateCharactersUsingPatternMatch(chars: List[Char]): List[(Char,Int)] = {
    chars map{ char => (char,chars.count(_ == char))} distinct
  }


}
