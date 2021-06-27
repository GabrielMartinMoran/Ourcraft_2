package com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.coins;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.CustomItem;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.CustomItemsModelData;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

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
