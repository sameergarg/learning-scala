package katas.designpatterns.decorator.oo;

import java.math.BigDecimal;

public interface Calculator {

    default Number add(int a, int b) {
        return a + b;
    }

    default Number subtract(int a, int b) {
        return a - b;
    }

    default Number multiply(int a, int b) {
        return a * b;
    }

    default Number divide(int a, int b) {
        return new BigDecimal(a).divide(new BigDecimal(b));
    }

}
