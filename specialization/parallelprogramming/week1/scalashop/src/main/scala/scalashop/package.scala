
import common._

import scala.collection.immutable.IndexedSeq

package object scalashop {

  /** The value of every pixel is represented as a 32 bit integer. */
  type RGBA = Int

  /** Returns the red component. */
  def red(c: RGBA): Int = (0xff000000 & c) >>> 24

  /** Returns the green component. */
  def green(c: RGBA): Int = (0x00ff0000 & c) >>> 16

  /** Returns the blue component. */
  def blue(c: RGBA): Int = (0x0000ff00 & c) >>> 8

  /** Returns the alpha component. */
  def alpha(c: RGBA): Int = (0x000000ff & c) >>> 0

  /** Used to create an RGBA value from separate components. */
  def rgba(r: Int, g: Int, b: Int, a: Int): RGBA = {
    (r << 24) | (g << 16) | (b << 8) | (a << 0)
  }

  /** Restricts the integer into the specified range. */
  def clamp(v: Int, min: Int, max: Int): Int = {
    if (v < min) min
    else if (v > max) max
    else v
  }

  /** Image is a two-dimensional matrix of pixel values. */
  class Img(val width: Int, val height: Int, private val data: Array[RGBA]) {
    def this(w: Int, h: Int) = this(w, h, new Array(w * h))
    def apply(x: Int, y: Int): RGBA = {
      //println(s"x=$x, y=$y, width=$width")
      data(y * width + x)}
    def update(x: Int, y: Int, c: RGBA): Unit = data(y * width + x) = c
  }

  /** Computes the blurred RGBA value of a single pixel of the input image. */
  def boxBlurKernel(src: Img, x: Int, y: Int, radius: Int): RGBA = {


    val neighboursPosition = {
      val minRow = clamp(x-radius, 0, src.width-1)
      val maxRow = clamp(x + radius, 0, src.width-1)
      val minCol = clamp(y - radius, 0, src.height-1)
      val maxCol = clamp(y + radius,0, src.height-1)
      //println(s"Region: x:[$minRow to $maxRow] and y:[$minCol to $maxCol]")
      for {
        row <- minRow to maxRow
        col <- minCol to maxCol
      } yield ((row, col))
    }

    val neighboursRGBA: Seq[(Int, Int)] => Seq[(Int, Int, Int, Int)] = positions => positions.map{
      case (x,y) => val rgba = src(x,y); (red(rgba), green(rgba), blue(rgba), alpha(rgba))
    }

    val sum: Seq[(Int, Int, Int, Int)] =>  (Int, Int, Int, Int) = neighbouringRGBA => neighbouringRGBA.foldLeft((0,0,0,0))((acc, rgba) =>
      (acc._1 + rgba._1, acc._2 + rgba._2, acc._3 + rgba._3, acc._4 + rgba._4))

    def average(combinedRGBA: (Int, Int, Int, Int), neighbours: Int): Int = combinedRGBA match {
      case (r,g,b,a) => rgba(r/neighbours, g/neighbours,  b/neighbours, a/neighbours)
    }

    //println(s"neighbours coordinates $neighboursPosition")
    val neighbours = neighboursRGBA(neighboursPosition)
    //println(s" rgba for neighbours: $neighbours")

    if(radius == 0) src(x,y)
    else average(sum(neighbours), neighbours.size)

  }


}
