package katas.patmat

import org.scalatest.{Matchers, FunSuite}

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

import katas.patmat.Huffman._

@RunWith(classOf[JUnitRunner])
class HuffmanSuite extends FunSuite with Matchers {
  trait TestTrees {
    val t1 = Fork(Leaf('a',2), Leaf('b',3), List('a','b'), 5)
    val t2 = Fork(Fork(Leaf('a',2), Leaf('b',3), List('a','b'), 5), Leaf('d',4), List('a','b','d'), 9)
  }

  test("weight of a larger tree") {
    new TestTrees {
      assert(weight(t1) === 5)
    }
  }

  test("chars of a larger tree") {
    new TestTrees {
      assert(chars(t2) === List('a','b','d'))
    }
  }

  test("string2chars(\"hello, world\")") {
    assert(string2Chars("hello, world") === List('h', 'e', 'l', 'l', 'o', ',', ' ', 'w', 'o', 'r', 'l', 'd'))
  }

   test("times chars") {
     times(List('a', 'b')) should contain('a',1)
     times(List('a', 'b')) should contain('b',1)
     times(List('a', 'b','a')) should contain('a',2)
     times(List('a', 'b','a')) should contain('b',1)

   }

  test("makeOrderedLeafList for some frequency table") {
    assert(makeOrderedLeafList(List(('t', 2), ('e', 1), ('x', 3))) === List(Leaf('e',1), Leaf('t',2), Leaf('x',3)))
  }

  test("combine of some leaf list") {
    val leaflist = List(Leaf('e', 1), Leaf('t', 2), Leaf('x', 4))
    assert(combine(leaflist) === List(Fork(Leaf('e',1),Leaf('t',2),List('e', 't'),3), Leaf('x',4)))
  }

  test("createCodeTree ") {
    new TestTrees {
      createCodeTree(string2Chars("aabbb")) should be(t1)
      createCodeTree(string2Chars("ababb")) should be(t1)
      createCodeTree(string2Chars("aabbbdddd")) should be(t2)
      createCodeTree(string2Chars("ababddddb")) should be(t2)
    }

  }

  test("decode"){
    //decode(createCodeTree(string2Chars("AAAAAAAABBBCDEFGH")),List(one,zero,zero,zero,one,zero,one,zero)) should be(List("B","A","C"))
    decode(createCodeTree(string2Chars("aabbbdddd")),List(1,0,1)) should be(List('d','b'))
    decode(createCodeTree(string2Chars("aabbbdddd")),List(1,0,1)) should be(List('d','b'))
    decode(createCodeTree(string2Chars("aabbbdddd")),List(1,0,1,0,1,1)) should be(List('d','b','b','d'))
    println(decodedSecret)
  }

  test("decode and encode a very short text should be identity") {
    new TestTrees {
      assert(decode(t1, encode(t1)("ab".toList)) === "ab".toList)
    }
  }
}
