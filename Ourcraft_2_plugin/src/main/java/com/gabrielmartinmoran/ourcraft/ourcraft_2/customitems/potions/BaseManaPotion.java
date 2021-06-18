package com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems;

import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;

public class ManaBottle implements CustomItem {
    @Override
    public ItemStack getItem() {
        ItemStack item = new ItemStack(Material.POTION, 1);
        PotionMeta meta = (PotionMeta) item.getItemMeta();
        meta.setDisplayName("Botella de mana");
        meta.setColor(Color.fromRGB(3, 152, 252));
        item.setItemMeta(meta);
        NBTItem nbt = new NBTItem(item);
        nbt.setBoolean("isManaBottle", true);
        return nbt.getItem();
    }

    @Override
    public Recipe getRecipe() {
        return null;
    }
}
