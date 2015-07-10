package katas.designpatterns.decorator.classic;

import java.math.BigDecimal;

/**
 *
 */
public class LoggingCalculator implements Calculator {

    private Calculator calculator;

    public LoggingCalculator(Calculator calculator) {
        this.calculator = calculator;
    }

    @Override
    public Number add(int a, int b) {
        Number result = calculator.add(a,b);
        System.out.println(""+a+" + "+b+"="+result);
        return result;
    }

    @Override
    public Number subtract(int a, int b) {
        Number result = calculator.subtract(a,b);
        System.out.println(""+a+" - "+b+"="+result);
        return result;
    }

    @Override
    public Number multiply(int a, int b) {
        Number result = calculator.multiply(a,b);
        System.out.println(""+a+" * "+b+"="+result);
        return result;
    }

    @Override
    public Number divide(int a, int b) {
        Number result = calculator.divide(a, b);
        System.out.println(""+a+" / "+b+"="+result);
        return result;
    }
}
