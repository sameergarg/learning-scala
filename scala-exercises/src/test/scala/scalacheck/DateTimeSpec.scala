package scalacheck

import org.scalatest.WordSpec
import org.scalatest.prop.Checkers
import com.fortysevendeg.scalacheck.datetime.joda.ArbitraryJoda._
import org.joda.time.DateTime
import org.scalacheck.Prop.forAll

class DateTimeSpec extends WordSpec with Checkers {

  "DateTime generator" should {
    "generate dateTime" in {
      check {
        forAll { dt: DateTime =>
          (dt.getDayOfMonth >= 1 && dt.getDayOfMonth <= 31) == true

        }
      }
    }

    "add granular field" in {
      import java.time._
      import com.fortysevendeg.scalacheck.datetime.jdk8.ArbitraryJdk8._
      import com.fortysevendeg.scalacheck.datetime.jdk8.granularity.years

      import org.scalacheck.Prop.forAll

      check {
        forAll { zdt: ZonedDateTime =>
          (zdt.getMonth == Month.JANUARY) &&
            (zdt.getDayOfMonth == 1
              ) &&
            (zdt.getHour == 0
              ) &&
            (zdt.getMinute == 0
              ) &&
            (zdt.getSecond == 0
              ) &&
            (zdt.getNano == 0
              )
        }
      }
    }

    "create range" in {
      import org.joda.time._
      import com.fortysevendeg.scalacheck.datetime.instances.joda._
      import com.fortysevendeg.scalacheck.datetime.GenDateTime.genDateTimeWithinRange
      import org.scalacheck.Prop.forAll

      val from = new DateTime(2016, 1, 1, 0, 0)
      val range = Period.years(1)

      check {
        forAll(genDateTimeWithinRange(from, range)) { dt =>
          dt.getYear == 2016

        }
      }
    }

    "generate granular date range" in {
      import org.joda.time._
      import com.fortysevendeg.scalacheck.datetime.instances.joda._
      import com.fortysevendeg.scalacheck.datetime.GenDateTime.genDateTimeWithinRange
      import com.fortysevendeg.scalacheck.datetime.joda.granularity.days
      import org.scalacheck.Prop.forAll

      val from = new DateTime(2016, 1, 1, 0, 0)
      val range = Period.years(1)

      check {
        forAll(genDateTimeWithinRange(from, range)) { dt =>
          (dt.getYear == 2016
            ) &&
            (dt.getHourOfDay == 0
              ) &&
            (dt.getMinuteOfHour == 0
              ) &&
            (dt.getSecondOfMinute == 0
              ) &&
            (dt.getMillisOfSecond == 0
              )
        }
      }
    }

  }
}
