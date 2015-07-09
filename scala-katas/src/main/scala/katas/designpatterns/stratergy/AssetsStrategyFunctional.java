package katas.designpatterns.stratergy;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 *
 */
public class AssetsStrategyFunctional {

    public static final List<Asset> assets = Arrays.asList(
            new Asset(BOND$.MODULE$, 1000),
            new Asset(BOND$.MODULE$, 2000),
            new Asset(STOCK$.MODULE$, 5000),
            new Asset(STOCK$.MODULE$, 10000));

    //e.g
    private Predicate<Asset> bondsOnly = asset -> asset.assetType() == BOND$.MODULE$;

    //naive
    public static int assetValuesOf(final List<Asset> assets, Predicate<Asset> assetsSelector) {
        return assets.stream()
                .filter(assetsSelector)
                .mapToInt(Asset::value)
                .sum();
    }


}
