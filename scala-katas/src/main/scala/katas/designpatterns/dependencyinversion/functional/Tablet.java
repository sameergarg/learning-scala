package katas.designpatterns.dependencyinversion.functional;

import katas.designpatterns.dependencyinversion.Charger;

import java.util.function.Supplier;

/**
 *
 */
public class Tablet {

    public String plugIn(Charger charger) {
        return charger.charge();
    }
}
