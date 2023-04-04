package com.gabrielmartinmoran.ourcraft.ourcraft_2.items;

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
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

public class PurifiedWaterBottle extends CustomItem {

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
        ItemStack waterBottle = new ItemStack(Material.POTION, 1);
        PotionMeta meta = (PotionMeta) waterBottle.getItemMeta();
        meta.setBasePotionData(new PotionData(PotionType.WATER));
        waterBottle.setItemMeta(meta);
        Recipe recipe = new FurnaceRecipe(nsKey, this.getItem(), new RecipeChoice.ExactChoice(waterBottle), 5, 200);
        return recipe;
    }
}
