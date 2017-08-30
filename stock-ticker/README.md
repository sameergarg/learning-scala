# The following URL provides one year historical stock price quotes from Yahoo finance

```
def pricesURL(businessDate : java.time.LocalDate, ticker: String) : String = {

  val lastYear = businessDate.minusYears(1)

  val url =f"http://real-chart.finance.yahoo.com/table.csv?s=$ticker&a=${lastYear.getMonthValue}&b=${lastYear.getDayOfMonth}&c=${lastYear.getYear}&d=${today.getMonthValue}&e=${today.getDayOfMonth}&f=${today.getYear}&g=d&ignore=.csv"

}

 
```
Task

Please write Scala functions that will return

 

* 1 - 1 year historic prices given a ticker */
```
def dailyPrices(ticker: String) : List[Double] 
```
 

* 2- daily returns, where return = ( Price_Today – Price_Yesterday)/Price_Yesterday */
```
def returns(ticker:String) : Seq[Double]
```
 

* 3 – 1 year mean returns */
```
def meanReturn(ticker:String): Double
```
 

/* example usage */
```
val googleDailyPrices = dailyPrices("GOOG")

val googleDailyReturns = returns("GOOG")

val googleAverageReturns = meanrReturn("GOOG")

 ```

Notes

1) Please treat this as you would approach any other task at work

2) Indicative time is 1 hour

3) Feel free to adapt function signatures to make them more functional