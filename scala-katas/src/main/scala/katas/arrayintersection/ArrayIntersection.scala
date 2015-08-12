package katas.arrayintersection

/**
 *
 */
object ArrayIntersection {

  def intersection(a: Array[Int], b: Array[Int]): Seq[Int] = {
    if(a.isEmpty) b
    else if(b.isEmpty) a
    else {
      val invertedArray: Array[Int] = fillInvertedArrayWithArrayAEntries(a, b)

      for{
        index <-  (0 to b.length-1)
        if(invertedArray(b(index)) == 0)
      } yield b(index)

    }
  }

  def fillInvertedArrayWithArrayAEntries(a: Array[Int], b: Array[Int]): Array[Int] = {
    val invertedArrayLength = Math.max(a(a.length - 1), b(b.length - 1)) + 1
    val invertedArray = Array.fill(invertedArrayLength)(-1)

    (0 to a.length - 1).foreach(index => invertedArray(a(index)) = 0)

    invertedArray
  }
}
