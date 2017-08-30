package katas.fileoperations

import org.scalatest.{Matchers, WordSpec}

class GzipSpec extends WordSpec with Matchers {

  "Gzip" should {
    "compress and decompress contents" in {
      Gzip.decompress(Gzip.compress("hello".getBytes)) shouldBe Some("hello")
    }

    "compress and decompress contents in ASCII" in {
      Gzip.decompress(Gzip.compress("hello".getBytes("US-ASCII"))) shouldBe Some("hello")
    }

    "compress big text" in {
      val input = FileGenerator.generateByteArray(10000000).toArray
      Gzip.compress(input).size should be < input.size
    }
  }
}
