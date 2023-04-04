package com.gabrielmartinmoran.ourcraft.ourcraft_2.items;

import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.meta.ItemMeta;

public class CombatPearl extends CustomItem {

    public final static String IS_COMBAT_PEARL_TAG = "isCombatPearl";
    public final static double DAMAGE_RADIUS = 6d;
    public final static double DAMAGE_AMOUNT = 10d;

    @Override
    public ItemStack getItem() {
        ItemStack item = new ItemStack(Material.ENDER_PEARL, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("Perla de combate");
        meta.setCustomModelData(CustomItemsModelData.COMBAT_PEARL.getModelData());
        item.setItemMeta(meta);
        NBTItem nbt = new NBTItem(item);
        nbt.setBoolean(IS_COMBAT_PEARL_TAG, true);
        return nbt.getItem();
    }

    @Override
    public Recipe getRecipe() {
        return null;
    }
}
