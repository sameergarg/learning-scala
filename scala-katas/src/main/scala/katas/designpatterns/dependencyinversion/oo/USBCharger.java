package katas.designpatterns.dependencyinversion.oo;

import katas.designpatterns.dependencyinversion.Charger;

/**
 *
 */
public class USBCharger implements Charger {
    @Override
    public String charge() {
        return "using USB port";
    }
}
