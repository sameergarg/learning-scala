import java.time.LocalDate

/**
  *
  */
class StockTicker {

  def pricesURL(businessDate: java.time.LocalDate, ticker: String): String = {
    val lastYear = businessDate.minusYears(1)
    val today = LocalDate.now()
    val url = f"http://real-chart.finance.yahoo.com/table.csv?s=$ticker&a=${lastYear.getMonthValue}&b=${lastYear.getDayOfMonth}&c=${lastYear.getYear}&d=${today.getMonthValue}&e=${today.getDayOfMonth}&f=${today.getYear}&g=d&ignore=.csv"

  }

  def dailyPrices(ticker: String) : List[Double]

  def returns(ticker:String) : Seq[Double]

  def meanReturn(ticker:String): Double

  val googleDailyPrices = dailyPrices("GOOG")

  val googleDailyReturns = returns("GOOG")

  val googleAverageReturns = meanrReturn("GOOG")
}
