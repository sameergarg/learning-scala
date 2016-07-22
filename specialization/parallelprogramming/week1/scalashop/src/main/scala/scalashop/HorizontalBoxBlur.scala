package scalashop

import org.scalameter._
import common._

import scala.collection.immutable.IndexedSeq

object HorizontalBoxBlurRunner {

  val standardConfig = config(
    Key.exec.minWarmupRuns -> 5,
    Key.exec.maxWarmupRuns -> 10,
    Key.exec.benchRuns -> 10,
    Key.verbose -> true
  ) withWarmer(new Warmer.Default)

  def main(args: Array[String]): Unit = {
    val radius = 3
    val width = 1920
    val height = 1080
    val src = new Img(width, height)
    val dst = new Img(width, height)
    val seqtime = standardConfig measure {
      HorizontalBoxBlur.blur(src, dst, 0, height, radius)
    }
    //println(s"sequential blur time: $seqtime ms")

    val numTasks = 32
    val partime = standardConfig measure {
      HorizontalBoxBlur.parBlur(src, dst, numTasks, radius)
    }
    //println(s"fork/join blur time: $partime ms")
    //println(s"speedup: ${seqtime / partime}")
  }
}


/** A simple, trivially parallelizable computation. */
object HorizontalBoxBlur {

  /** Blurs the rows of the source image `src` into the destination image `dst`,
   *  starting with `from` and ending with `end` (non-inclusive).
   *
   *  Within each row, `blur` traverses the pixels by going from left to right.
   */
  def blur(src: Img, dst: Img, from: Int, end: Int, radius: Int): Unit = {
    (from to Math.min(end-1, src.height)).foreach(y =>
      (0 to src.width -1).foreach { x =>
        //println(s" for x:$x ,y:$y")
        val rgba1: RGBA = boxBlurKernel(src, x, y, radius)
        //print(s"rgba: $rgba1")
        dst.update(x, y, rgba1)
      }
    )
  }

  /** Blurs the rows of the source image in parallel using `numTasks` tasks.
   *
   *  Parallelization is done by stripping the source image `src` into
   *  `numTasks` separate strips, where each strip is composed of some number of
   *  rows.
   */
  def parBlur(src: Img, dst: Img, numTasks: Int, radius: Int): Unit = {
    val splitPoints: Range = Range(0, src.height+1).by(numTasks)
    val strips: IndexedSeq[(RGBA, RGBA)] = splitPoints.zip(splitPoints.tail)
    strips.map{
      case (from, end) => task(blur(src, dst, from, end, radius))
    }.foreach(_.join())
  }

}
