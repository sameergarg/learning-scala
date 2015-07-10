package katas.designpatterns.decorator.classic;

import java.math.BigDecimal;

/**
 * Created by sameer on 10/07/15.
 */
public class DefaultCalculator implements Calculator {


    @Override
    public Number add(int a, int b) {
        return a + b;
    }

    @Override
    public Number subtract(int a, int b) {
        return a - b;
    }

    @Override
    public Number multiply(int a, int b) {
        return a * b;
    }

    @Override
    public Number divide(int a, int b) {
        return new BigDecimal(a).divide(new BigDecimal(b));
    }
}
