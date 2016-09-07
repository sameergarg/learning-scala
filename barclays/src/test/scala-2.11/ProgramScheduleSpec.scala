import org.scalatest.{FunSuite, Matchers, WordSpec}

/**
  * Created by samegarg on 22/08/2016.
  */
class ProgramScheduleSpec extends WordSpec with Matchers {

  "start and end times should be combined" in {
    ProgramSchedule.combineTimes(Array(1,2,3), Array(2,3,4)) shouldBe Array((1,2), (2,3), (3,4))
  }

  "should sort programmes by time" in {

  }
}
