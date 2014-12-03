package katas.maps

import org.scalatest.{Matchers, WordSpec}

import scala.util.Try

/**
 * Created by sameer on 02/12/14.
 */
class MapExampleTest extends WordSpec with Matchers {


  "A map example" should {
    "demonstrate conversion of map(String->Option(String)) to  map(String->Boolean)" in {
      import MapUtils._

      val mapOfOptionalBoolean = Map("a"->Some("true"),"b"->Some("false"), "c"->Some("1"))

      print(mapOfOptionalBoolean) shouldBe(Map("a"->true,"b"->false,"c"->false))
    }
  }

}
