object Week1 {

  val radius = 1
  val x= 2
  val y = 2

  val positions = for {
    row <- x-radius to x+radius
    col <- y-radius to y+radius

  } yield ((row, col))
  val flatten: Set[(Int, Int)] = positions.toSet

  val range = (0 to 9)

  val split: Range = range.by(2)
  val adjusted = if(!split.contains(range.end)) split.:+(range.end) else split

  println(range.isInclusive)

  val splits = split.zip(range.tail)


}