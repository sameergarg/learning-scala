package katas.designpatterns.dependencyinversion.oo;

import katas.designpatterns.dependencyinversion.Charger;

/**
 *
 */
public class LightningPortCharger implements Charger {

    @Override
    public String charge() {
        return "using lightning port";
    }
}
