package com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.ores;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.Main;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.CustomItem;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.CustomItemsModelData;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class RawGold extends CustomItem {

    @Override
    public ItemStack getItem() {
        ItemStack item = new ItemStack(Material.FIREWORK_STAR, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("Oro crudo");
        meta.setCustomModelData(CustomItemsModelData.RAW_GOLD.getModelData());
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public Recipe getRecipe() {
        NamespacedKey nsKey = new NamespacedKey(JavaPlugin.getPlugin(Main.class),"raw_gold_from_raw_gold_block");
        ItemStack item = this.getItem();
        item.setAmount(9);
        ShapelessRecipe recipe = new ShapelessRecipe(nsKey, item);
        RecipeChoice.ExactChoice rawBlock = new RecipeChoice.ExactChoice(new RawGoldBlock().getItem());
        recipe.addIngredient(rawBlock);
        return recipe;
    }
}
