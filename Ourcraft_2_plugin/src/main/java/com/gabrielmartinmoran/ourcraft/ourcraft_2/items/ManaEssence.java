package com.gabrielmartinmoran.ourcraft.ourcraft_2.items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.meta.ItemMeta;

public class ManaEssence extends CustomItem {
    @Override
    public ItemStack getItem() {
        ItemStack item = new ItemStack(Material.FIREWORK_STAR, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("Esencia de maná");
        meta.setCustomModelData(CustomItemsModelData.MANA_ESSENCE.getModelData());
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public Recipe getRecipe() {
        return null;
    }
}
