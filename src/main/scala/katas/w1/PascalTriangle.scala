package katas.w1

import scala.annotation.tailrec

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

  def tailRecursiveAt(row: Int, col: Int) : Int = {
    //@tailrec
    def position(row: Int, col: Int, first: Int, invalidPositionValue: Int): Int = {
      if(col<0 || col>row){
        return invalidPositionValue
      }

      if(row == 0){
        return first
      }

      position(row-1,col-1,1,0 ) + position(row-1, col, 1,0)

    }
    position(row, col,1,0)
  }
}
