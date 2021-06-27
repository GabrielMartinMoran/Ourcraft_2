package com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.ores;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.Main;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.CustomItem;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.CustomItemsModelData;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class RawGoldBlock extends CustomItem {

    @Override
    public ItemStack getItem() {
        ItemStack item = new ItemStack(Material.FIREWORK_STAR, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("Bloque de oro crudo");
        meta.setCustomModelData(CustomItemsModelData.RAW_GOLD_BLOCK.getModelData());
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public Recipe getRecipe() {
        NamespacedKey nsKey = new NamespacedKey(JavaPlugin.getPlugin(Main.class),"raw_gold_block_from_raw_gold");
        ShapelessRecipe recipe = new ShapelessRecipe(nsKey, this.getItem());
        RecipeChoice.ExactChoice raw = new RecipeChoice.ExactChoice(new RawGold().getItem());
        for (int i = 0; i < 9; i++) recipe.addIngredient(raw);
        return recipe;
    }
}
