package com.gabrielmartinmoran.ourcraft.ourcraft_2.items.coins;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.CustomItemsModelData;

public class PlatinumCoin extends BaseCoin {

    @Override
    public String getName() {
        return "Moneda de platino";
    }

    @Override
    public String getLore() {
        return "Equivale a 10 monedas de oro";
    }

    @Override
    public CustomItemsModelData getModelData() {
        return CustomItemsModelData.PLATINUM_COIN;
    }
}
