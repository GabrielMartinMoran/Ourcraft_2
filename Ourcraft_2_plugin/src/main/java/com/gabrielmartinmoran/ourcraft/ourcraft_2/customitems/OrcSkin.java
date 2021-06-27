package com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.meta.ItemMeta;

public class OrcSkin extends CustomItem {
    @Override
    public ItemStack getItem() {
        ItemStack item = new ItemStack(Material.LEATHER, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("Piel de orco");
        meta.setCustomModelData(CustomItemsModelData.ORC_SKIN.getModelData());
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public Recipe getRecipe() {
        return null;
    }
}
