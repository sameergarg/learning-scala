package katas.designpatterns.decorator.functional;

import scala.Function2;

import java.math.BigDecimal;

/**
 *Decorator is useful when we’ve got an existing class that we need to add some
 behavior to but we can’t change the existing class. We may want to introduce
 a breaking change, but we can’t change every other part of the system where
 the class is used. Or the class may be part of a library that we can’t, or don’t
 want to, modify.
 */
public class DefaultFunctionalCalculator {

    interface  FunctionalCalculator {
        Number calculate(Number a, Number b);
    }

    public FunctionalCalculator add = (a, b) -> a.intValue() + b.intValue();

    public FunctionalCalculator subtract = (a,b) -> a.intValue() - b.intValue();

    public FunctionalCalculator multiply = (a,b) -> a.intValue() * b.intValue();

    public FunctionalCalculator divide = (a,b) -> new BigDecimal(a.toString()).divide(new BigDecimal(b.toString()));

    public Number loggingCalculator(FunctionalCalculator calc, Number a, Number b) {
        Number result = calc.calculate(a,b);
        System.out.println("Result of "+calc.toString()+"is:"+result);
        return result;
    }
}
