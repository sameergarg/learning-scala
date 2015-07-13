package katas.designpatterns.dependencyinversion.functional;

import katas.designpatterns.dependencyinversion.Charger;
import katas.designpatterns.dependencyinversion.oo.LightningPortCharger;
import org.junit.Test;

import java.util.function.Supplier;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 *
 */
public class TabletTest {

    @Test
    public void shouldChargeTablet() throws Exception {
        //given
        Charger charger = () -> "simple charger";

        Tablet tablet = new Tablet();


        String message = tablet.plugIn(charger);

        //then
        assertThat(message, is("simple chargerÊ"));
    }

    @Test
    public void shouldChargeTabletAgain() throws Exception {
        //given
        Supplier<String> charger = () -> "simple charger";

        Tablet tablet = new Tablet();


        String message = tablet.plugin2(charger);

        //then
        assertThat(message, is("simple chargerÊ"));
    }
}