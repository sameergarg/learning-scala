package katas.designpatterns.mapreduce;

import java.math.BigDecimal;

/**
 *
 */
public class StockInfo {
    private String symbol;
    private BigDecimal price;

    public StockInfo(String symbol, BigDecimal price) {
        this.symbol = symbol;
        this.price = price;
    }

    public String getSymbol() {
        return symbol;
    }

    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StockInfo stockInfo = (StockInfo) o;

        if (symbol != null ? !symbol.equals(stockInfo.symbol) : stockInfo.symbol != null) return false;
        return !(price != null ? !price.equals(stockInfo.price) : stockInfo.price != null);

    }

    @Override
    public int hashCode() {
        int result = symbol != null ? symbol.hashCode() : 0;
        result = 31 * result + (price != null ? price.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "StockInfo{" +
                "symbol='" + symbol + '\'' +
                ", price=" + price +
                '}';
    }
}
