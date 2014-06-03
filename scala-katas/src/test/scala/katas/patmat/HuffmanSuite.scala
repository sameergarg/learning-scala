package katas.patmat

import org.scalatest.{Matchers, FunSuite}

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

import katas.patmat.Huffman._
import katas.patmat.Huffman.Leaf
import katas.patmat.Huffman.Fork

@RunWith(classOf[JUnitRunner])
class HuffmanSuite extends FunSuite with Matchers {

  trait TestTrees {
    val t1 = Fork(Leaf('a', 2), Leaf('b', 3), List('a', 'b'), 5)
    val t2 = Fork(Fork(Leaf('a', 2), Leaf('b', 3), List('a', 'b'), 5), Leaf('d', 4), List('a', 'b', 'd'), 9)
  }

  test("weight of a larger tree") {
    new TestTrees {
      assert(weight(t1) === 5)
    }
  }

  test("chars of a larger tree") {
    new TestTrees {
      assert(chars(t2) === List('a', 'b', 'd'))
    }
  }

  test("string2chars(\"hello, world\")") {
    assert(string2Chars("hello, world") === List('h', 'e', 'l', 'l', 'o', ',', ' ', 'w', 'o', 'r', 'l', 'd'))
  }

  test("times chars") {
    times(List('a', 'b')) should contain('a', 1)
    times(List('a', 'b')) should contain('b', 1)
    times(List('a', 'b', 'a')) should contain('a', 2)
    times(List('a', 'b', 'a')) should contain('b', 1)

  }

  test("makeOrderedLeafList for some frequency table") {
    assert(makeOrderedLeafList(List(('t', 2), ('e', 1), ('x', 3))) === List(Leaf('e', 1), Leaf('t', 2), Leaf('x', 3)))
  }

  test("combine of some leaf list") {
    val leaflist = List(Leaf('e', 1), Leaf('t', 2), Leaf('x', 4))
    assert(combine(leaflist) === List(Fork(Leaf('e', 1), Leaf('t', 2), List('e', 't'), 3), Leaf('x', 4)))
  }

  test("createCodeTree ") {
    new TestTrees {
      createCodeTree(string2Chars("aabbb")) should be(t1)
      createCodeTree(string2Chars("ababb")) should be(t1)
      createCodeTree(string2Chars("aabbbdddd")) should be(t2)
      createCodeTree(string2Chars("ababddddb")) should be(t2)
    }

  }

  test("decode") {
    decode(createCodeTree(string2Chars("aabbbdddd")), List(1)) should be(List('d'))
    decode(createCodeTree(string2Chars("aabbbdddd")), List(0,0)) should be(List('a'))
    decode(createCodeTree(string2Chars("aabbbdddd")), List(0,1)) should be(List('b'))
    decode(createCodeTree(string2Chars("aabbbdddd")), List(1, 0, 1)) should be(List('d', 'b'))
    decode(createCodeTree(string2Chars("aabbbdddd")), List(1, 0, 0)) should be(List('d', 'a'))
    decode(createCodeTree(string2Chars("aabbbdddd")), List(1, 0, 1, 0, 1)) should be(List('d', 'b', 'b'))
    decode(createCodeTree(string2Chars("aabbbdddd")), List(1, 0, 1, 0, 1, 1)) should be(List('d', 'b', 'b', 'd'))
    println(decodedSecret)
  }

  test("encode") {
    encode(createCodeTree(string2Chars("aabbbdddd")))(List('d')) should be(List(1))
    encode(createCodeTree(string2Chars("aabbbdddd")))(List('a')) should be(List(0, 0))
    encode(createCodeTree(string2Chars("aabbbdddd")))(List('d', 'b')) should be(List(1, 0, 1))
  }

  test("decode and encode a very short text should be identity") {
    new TestTrees {
      assert(decode(t1, encode(t1)("ab".toList)) === "ab".toList)
    }
  }

  test("codeBits") {
    val codeTable = codeBits(List[(Char, List[Bit])](('a', List(0, 1, 1, 0)), ('b', List(1, 1, 1, 1))))(_)
    codeTable('a') should be(List[Bit](0, 1, 1, 0))
    codeTable('c') should be(Nil)
  }

  test("mergeCodeTables") {
    mergeCodeTables(List(('a', List(0, 1, 1, 0))), List(('a', List(0, 1, 1, 0)))) should be(List(('a', List(0, 1, 1, 0))))
    mergeCodeTables(List(('a', List(0, 1, 1, 0))), List(('b', List(1, 1, 1, 1)))) should be(List(('a', List(0, 1, 1, 0)), (('b', List(1, 1, 1, 1)))))
    mergeCodeTables(List(('a', List(0, 1, 1, 0)), ('c', List(1))), List(('b', List(1, 1, 1, 1)), ('c', List(1)))) should be(List(
      ('a', List(0, 1, 1, 0)),
      ('b', List(1, 1, 1, 1)),
      ('c', List(1))
    ))
  }

  test("convert") {
    new TestTrees {
      convert(t1) should be(List(('a', List(0)), ('b', List(1))))
      convert(t2) should be(List(('b', List(0, 1)), ('a', List(0, 0)), ('d', List(1))))
    }
  }

  test("quickEncode") {
    new TestTrees {
      quickEncode(t2)(string2Chars("aaabbbddd")) should be(List(0,0,0,0,0,0,0,1,0,1,0,1,1,1,1))
    }
  }

}
