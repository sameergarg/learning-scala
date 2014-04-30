package katas.w1

/**
 *
 * User: sameer
 * Date: 30/04/2014
 * Time: 20:52
 */
object PascalTriangle {

  def at(row: Int, col: Int) : Int = {

    if(col<0 || col>row){
      return 0
    }

    if(row == 0){
        return 1
    }

    at(row-1,col-1) + at(row-1, col)

  }

  def position(row: Int, col: Int): Int = {
    1
  }

  def tailRecursiveAt(row: Int, col: Int) : Int = {
    position(row-1,col-1) + position(row-1, col)
  }
}
