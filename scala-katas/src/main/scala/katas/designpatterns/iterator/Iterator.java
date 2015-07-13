package katas.designpatterns.iterator;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * From collection of prices and find total discounted price values
 */
public class Iterator {

    final List<BigDecimal> prices = Arrays.asList(
            new BigDecimal("10"), new BigDecimal("30"), new BigDecimal("17"),
            new BigDecimal("20"), new BigDecimal("15"), new BigDecimal("18"),
            new BigDecimal("45"), new BigDecimal("12"));

    public BigDecimal calculateTotalOfDiscountedPrices_imperative(
            BigDecimal greaterThan, BigDecimal discount) {
        BigDecimal totalOfDiscountedPrices = BigDecimal.ZERO;

        for(BigDecimal price : prices) {
            if(price.compareTo(greaterThan) > 0)
                totalOfDiscountedPrices =
                        totalOfDiscountedPrices.add(price.multiply(discount));
        }

        return totalOfDiscountedPrices;
    }

    public BigDecimal calculateTotalOfDiscountedPrices_declarative(
            BigDecimal greaterThan, BigDecimal discount) {
        return prices.stream()
                .filter(price -> price.compareTo(greaterThan)>0)
                .map(price -> price.multiply(discount))
                .reduce(BigDecimal.ZERO , BigDecimal:: add);
    }


}


