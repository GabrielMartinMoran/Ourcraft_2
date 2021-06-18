package com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.Main;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public class PurifiedWater implements CustomItem {

    public static final String IS_PURIFIED_WATER_TAG = "isPurifiedWater";

    @Override
    public ItemStack getItem() {
        ItemStack item = new ItemStack(Material.POTION, 1);
        PotionMeta meta = (PotionMeta) item.getItemMeta();
        meta.setDisplayName("Botella de agua purificada");
        meta.setColor(Color.fromRGB(117, 200, 255));
        item.setItemMeta(meta);
        NBTItem nbt = new NBTItem(item);
        nbt.setBoolean(IS_PURIFIED_WATER_TAG, true);
        return nbt.getItem();
    }

    @Override
    public Recipe getRecipe() {
        NamespacedKey nsKey = new NamespacedKey(JavaPlugin.getPlugin(Main.class),"purified_water_bottle");
        RecipeChoice.ExactChoice waterBottle = new RecipeChoice.ExactChoice(new ItemStack(Material.POTION, 1, (byte)0));
        Recipe recipe = new FurnaceRecipe(nsKey, this.getItem(), waterBottle, 5, 200);
        return recipe;
    }
}
