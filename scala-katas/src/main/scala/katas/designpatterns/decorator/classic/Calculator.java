package katas.designpatterns.decorator.classic;

import java.math.BigDecimal;

public interface Calculator {

    Number add(int a, int b);

    Number subtract(int a, int b);

    Number multiply(int a, int b);

    Number divide(int a, int b);

}
