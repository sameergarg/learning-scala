package katas.designpatterns.mapreduce;


import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Predicate;

/**
 *
 */
public class MapReducePattern {

    private static final List<String> symbols = Arrays.asList("AMD", "HPQ", "IBM", "TXN", "VMW", "XRX", "AAPL", "ADBE",
            "AMZN", "CRAY", "CSCO", "DELL", "GOOG", "INTC", "INTU",
            "MSFT", "ORCL", "TIBX", "VRSN", "YHOO");

    /**
     * Given a list of ticker symbols,
     * let’s pick the highest-priced stock whose value is less than $500
     * @param minimumStockPrice
     */
    public String selectHighestOfAllStockPriceLessThan(int minimumStockPrice){
        return symbols
                .stream()
                //.parallelStream()
                .map(symbol -> new StockInfo(symbol, YahooFinance$.MODULE$.priceFor(symbol).bigDecimal()))
                .filter(priceLessThan(minimumStockPrice))
                .reduce(comparePrices())
                .map(StockInfo::toString)
                .orElse("There is no stock less than " + minimumStockPrice);

    }

    private BinaryOperator<StockInfo> comparePrices() {
        return (stock1, stock2) -> stock1.getPrice().compareTo(stock2.getPrice()) > 0 ? stock1 : stock2;
    }

    public Predicate<StockInfo> priceLessThan(int minimumStockPrice) {
        return stock -> stock.getPrice().compareTo(BigDecimal.valueOf(minimumStockPrice)) < 0;
    }

    public Comparator<StockInfo> stockPriceComparator() {
        return (stock1, stock2) -> stock1.getPrice().compareTo(stock2.getPrice());
    }
}
