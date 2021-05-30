package com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.coins;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.CustomItem;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.CustomItemsModelData;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

public class GoldenCoin extends BaseCoin {

    protected Material material = Material.YELLOW_DYE;
    protected String name = "Moneda de oro";
    protected String lore = "Equivale a 10 monedas de plata";
    protected CustomItemsModelData modelData = CustomItemsModelData.GOLDEN_COIN;
}
