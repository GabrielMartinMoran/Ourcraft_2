package com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.Main;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public class ReinforcedLeather extends CustomItem {
    @Override
    public ItemStack getItem() {
        ItemStack item = new ItemStack(Material.LEATHER, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("Cuero reforzado");
        meta.setCustomModelData(CustomItemsModelData.REINFORCED_LEATHER.getModelData());
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public Recipe getRecipe() {
        NamespacedKey nsKey = new NamespacedKey(JavaPlugin.getPlugin(Main.class),"reinforced_leather");
        ShapedRecipe recipe = new ShapedRecipe(nsKey, getItem());
        RecipeChoice.ExactChoice orcSkin = new RecipeChoice.ExactChoice((new OrcSkin()).getItem());
        recipe.shape("nln","shs","nln");
        recipe.setIngredient('n', Material.IRON_NUGGET);
        recipe.setIngredient('s', orcSkin);
        recipe.setIngredient('l', Material.LEATHER);
        recipe.setIngredient('h', Material.RABBIT_HIDE);
        return recipe;
    }
}
