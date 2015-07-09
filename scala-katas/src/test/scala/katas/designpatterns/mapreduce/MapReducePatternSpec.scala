package katas.designpatterns.mapreduce

import java.util

import org.scalatest.{Matchers, FlatSpec}

/**
 *
 */
class MapReducePatternSpec extends FlatSpec with Matchers {

  "MapReduce" should "select a stock which is highest-priced stock whose value is less than specified price" in new MapReducePattern {
    selectHighestOfAllStockPriceLessThan(500) should include ("AMZN")
  }

  it should "not find any stock whose price is lower than 0" in new MapReducePattern {
    selectHighestOfAllStockPriceLessThan(0) shouldBe "There is no stock less than 0"
  }

  it should "compare two stock prices" in new MapReducePattern {
    val stocks = util.Arrays.asList(
      new StockInfo("A", new java.math.BigDecimal(100)),
      new StockInfo("C", new java.math.BigDecimal(300)),
      new StockInfo("B", new java.math.BigDecimal(200)))

    stocks.stream().sorted(this.stockPriceComparator()).findFirst().get().getSymbol shouldBe "A"
  }

}
