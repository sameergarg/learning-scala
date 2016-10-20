package katas.fileoperations

import java.io._
import java.util.zip.{GZIPInputStream, GZIPOutputStream}

import scala.util.{Random, Try}

object Gzip {

  def compress(input: Array[Byte]): Array[Byte] = {
    val bos = new ByteArrayOutputStream(input.length)
    val gzip = new GZIPOutputStream(bos)
    gzip.write(input)
    gzip.close()
    val compressed = bos.toByteArray
    bos.close()
    compressed
  }

  def decompress(compressed: Array[Byte]): Option[String] =
    Try {
      val inputStream = new GZIPInputStream(new ByteArrayInputStream(compressed))
      scala.io.Source.fromInputStream(inputStream).mkString
    }.toOption
}

object FileGenerator {

  def generateByteArray(size: Int): Stream[Byte] = Random.alphanumeric.map(_.toByte).take(size)

  def generateFileWithRandomContent(size: Int = 10000000, name: String = "byte-array4.txt") = {
    fileWithContent(name, Random.alphanumeric.map(_.toByte).take(size).toArray)
  }

  def fileWithContent(name: String, content: Array[Byte]) = {
    val file = new File(name)
    val bw = new BufferedWriter(new FileWriter(file))
    bw.write(content.toString)
    bw.close()
  }
}