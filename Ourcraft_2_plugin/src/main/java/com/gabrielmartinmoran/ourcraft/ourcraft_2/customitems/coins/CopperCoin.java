package com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.coins;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.CustomItem;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.CustomItemsModelData;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

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
