package com.gabrielmartinmoran.ourcraft.ourcraft_2.items.coins;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.CustomItemsModelData;

public class SilverCoin extends BaseCoin {

    @Override
    public String getName() {
        return "Moneda de plata";
    }

    @Override
    public String getLore() {
        return "Equivale a 10 monedas de cobre";
    }

    @Override
    public CustomItemsModelData getModelData() {
        return CustomItemsModelData.SILVER_COIN;
    }
}
