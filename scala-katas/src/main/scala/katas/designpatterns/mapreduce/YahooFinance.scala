package katas.designpatterns.mapreduce

import java.io.FileNotFoundException

import scala.io.Source
import scala.util.Try

/**
 *
 */
trait YahooFinance {

  def priceFor(symbol: String): BigDecimal= {

    val stockUrl = s"http://ichart.finance.yahoo.com/table.csv?s=$symbol"
    val averagePriceForTheDay = Try(Source.fromURL(stockUrl)).map{
      _.getLines()
          .slice(1,2)
          .mkString
          .split(",")
          .last
    }.getOrElse(throw new FileNotFoundException(s"There is no stock information for $symbol"))


    averagePriceForTheDay.toDouble
  }
}

object YahooFinance extends YahooFinance
