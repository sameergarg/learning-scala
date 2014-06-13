package katas

import katas.lists.DuplicateListIemCount
import org.scalatest.{Matchers, FunSuite}
import DuplicateListIemCount._

class DuplicateListIemCount$Test extends FunSuite with Matchers {

  test("count duplicates") {
    val characters = List('a','b','c','a','c','a','b','d')
    countDuplicateCharactersUsingGroupBy(characters) should contain allOf (('a',3),('b',2),('c',2),('d',1))
    countDuplicateCharactersUsingGroupBy2(characters) should contain allOf (('a',3),('b',2),('c',2),('d',1))
    countDuplicateCharactersUsingPatternMatch(characters) should contain allOf (('a',3),('b',2),('c',2),('d',1))
  }



}
