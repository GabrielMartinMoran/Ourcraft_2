package com.gabrielmartinmoran.ourcraft.ourcraft_2.items;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.Main;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class ReinforcedString extends CustomItem {
    @Override
    public ItemStack getItem() {
        ItemStack item = new ItemStack(Material.STRING, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("Cuerda reforzada");
        meta.setCustomModelData(CustomItemsModelData.REINFORCED_STRING.getModelData());
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public Recipe getRecipe() {
        NamespacedKey nsKey = new NamespacedKey(JavaPlugin.getPlugin(Main.class),"reinforced_string");
        ShapedRecipe recipe = new ShapedRecipe(nsKey, getItem());
        recipe.shape("sns","nsn","sns");
        recipe.setIngredient('n', Material.GOLD_NUGGET);
        recipe.setIngredient('s', Material.STRING);
        return recipe;
    }
}
