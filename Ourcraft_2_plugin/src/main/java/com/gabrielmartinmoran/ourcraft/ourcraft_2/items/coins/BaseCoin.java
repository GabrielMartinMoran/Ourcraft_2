package com.gabrielmartinmoran.ourcraft.ourcraft_2.items.coins;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.CustomItem;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.CustomItemsModelData;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public abstract class BaseCoin extends CustomItem {

    public abstract  String getName();

    public abstract String getLore();

    public abstract CustomItemsModelData getModelData();

    @Override
    public ItemStack getItem() {
        return this.getItem(1);
    }

    public ItemStack getItem(int amount) {
        ItemStack coin = new ItemStack(this.getMaterial(), amount);
        ItemMeta meta = coin.getItemMeta();
        meta.setDisplayName(this.getName());
        meta.setLore(Arrays.asList(this.getLore()));
        meta.setCustomModelData(this.getModelData().getModelData());
        coin.setItemMeta(meta);
        return coin;
    }

    @Override
    public Recipe getRecipe() {
        return null;
    }

    public Material getMaterial() {
        return Material.FIREWORK_STAR;
    }

}
