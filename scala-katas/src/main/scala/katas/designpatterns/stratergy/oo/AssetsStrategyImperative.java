package katas.designpatterns.stratergy.oo;

import katas.designpatterns.stratergy.Asset;
import katas.designpatterns.stratergy.AssetType;
import katas.designpatterns.stratergy.BOND$;
import katas.designpatterns.stratergy.STOCK$;

import java.util.Arrays;
import java.util.List;

/**
 *
 */
public class AssetsStrategyImperative {

    public static final List<Asset> assets = Arrays.asList(
            new Asset(BOND$.MODULE$, 1000),
            new Asset(BOND$.MODULE$, 2000),
            new Asset(STOCK$.MODULE$, 5000),
            new Asset(STOCK$.MODULE$, 10000));

    //without strategy
    public int totalBondValues(final List<Asset> assets) {
        return assetValues(assets, BOND$.MODULE$);
    }

    public int totalStockValues(final List<Asset> assets) {
        return assetValues(assets, STOCK$.MODULE$);
    }

    //naive
    public int assetValues(final List<Asset> assets, AssetType assetType) {
        int value = 0;
        for(Asset asset: assets){
            if(asset.assetType() == assetType){
                value += asset.value();
            }
        }
        return value;
    }

    //all
    public int totalAssetValuesOf(final List<Asset> assets) {
        return assets.stream()
                .mapToInt(asset -> asset.value())
                .sum();
    }


}
