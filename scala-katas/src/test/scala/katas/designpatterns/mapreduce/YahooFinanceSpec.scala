package katas.designpatterns.mapreduce

import org.scalatest.{Matchers, FlatSpec}

/**
 *
 */
class YahooFinanceSpec extends FlatSpec with Matchers {

  "YahooFinance" should "obtain stock for symbol" in new YahooFinance {
    priceFor("AMZN").doubleValue() should be (500.0 +- 100.0)
  }

}
