import java.lang.Math.abs
object NQueens {

  def queens(n: Int): Set[List[Int]] = {

    def placeQueens(k: Int): Set[List[Int]] = {
      if (k == 0) Set(List())
      else for {
        queenPositions <- placeQueens(k-1)
        col <- 0 until n
        if isSafe(col,queenPositions)
      } yield col::queenPositions
    }

    placeQueens(n)
  }
  /**
   * Queen is safe it it is is
   * - Not in the same column: col != list elements
   * - Not in the diagonal column i.e diff between
   * row in which to place queen and the row in which we are checking is not same as
   * column in which to place queen and the column in which we are checking(absolute value)
   * @param col
   * @param queens
   * @return
   */
  def isSafe(col: Int, queens: List[Int]): Boolean = {
    val row = queens.length
    val queensWithRow = queens.indices.reverse zip queens

    queensWithRow.forall {
      case (r, c) => col != c && row - r != abs(c - col)
    }
  }

  val positions = queens(4).toList

}