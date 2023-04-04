package com.gabrielmartinmoran.ourcraft.ourcraft_2.items.coins;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.CustomItemsModelData;

public class CopperCoin extends BaseCoin {

    @Override
    public String getName() {
        return "Moneda de cobre";
    }

    @Override
    public String getLore() {
        return "Utilizala como unidad de cambio";
    }

    @Override
    public CustomItemsModelData getModelData() {
        return CustomItemsModelData.COPPER_COIN;
    }
}
