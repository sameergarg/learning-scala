package katas.files

import java.io.{BufferedWriter, File, FileWriter}

import scala.util.Random

/**
  *
  */
object FileGenerator extends App {

  def generateByteArray(size: Int) = Random.alphanumeric.map(_.toByte).take(100000).toList.toString

  def generateFile() = {
    val file = new File("byte-array4.txt")
    val bw = new BufferedWriter(new FileWriter(file))
    bw.write(generateByteArray(10000000))
    bw.close()
  }

  println(List(1,2,3,4).toString)
  generateFile()

}
