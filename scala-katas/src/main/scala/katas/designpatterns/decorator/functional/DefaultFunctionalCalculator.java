package katas.designpatterns.decorator.functional;

import katas.designpatterns.decorator.oo.Calculator;
import scala.Function2;

import java.math.BigDecimal;

/**
 *Decorator is useful when we’ve got an existing class that we need to add some
 behavior to but we can’t change the existing class. We may want to introduce
 a breaking change, but we can’t change every other part of the system where
 the class is used. Or the class may be part of a library that we can’t, or don’t
 want to, modify.
 */
public class DefaultFunctionalCalculator implements Calculator {

    @Override
    public Number add(int a, int b) {
        Number result = Calculator.super.add(a, b);
        System.out.println(""+a+" + "+b+"="+result);
        return result;
    }

    @Override
    public Number subtract(int a, int b) {
        Number result = Calculator.super.subtract(a,b);
        System.out.println(""+a+" - "+b+"="+result);
        return result;
    }

    @Override
    public Number multiply(int a, int b) {
        Number result = Calculator.super.multiply(a,b);
        System.out.println(""+a+" * "+b+"="+result);
        return result;
    }

    @Override
    public Number divide(int a, int b) {
        Number result = Calculator.super.divide(a, b);
        System.out.println(""+a+" / "+b+"="+result);
        return result;
    }
}
