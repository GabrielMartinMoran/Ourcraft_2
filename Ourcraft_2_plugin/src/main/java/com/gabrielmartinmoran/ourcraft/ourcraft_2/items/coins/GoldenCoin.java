package com.gabrielmartinmoran.ourcraft.ourcraft_2.items.coins;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.CustomItemsModelData;

public class GoldenCoin extends BaseCoin {

    @Override
    public String getName() {
        return "Moneda de oro";
    }

    @Override
    public String getLore() {
        return "Equivale a 10 monedas de plata";
    }

    @Override
    public CustomItemsModelData getModelData() {
        return CustomItemsModelData.GOLDEN_COIN;
    }
}
