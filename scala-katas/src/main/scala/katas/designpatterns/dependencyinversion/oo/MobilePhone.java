package katas.designpatterns.dependencyinversion.oo;

import katas.designpatterns.dependencyinversion.Chargeable;
import katas.designpatterns.dependencyinversion.Charger;

/**
 *
 */
public class MobilePhone implements Chargeable {

    @Override
    public void plugIn(Charger charger) {
        charger.charge();
    }
}
