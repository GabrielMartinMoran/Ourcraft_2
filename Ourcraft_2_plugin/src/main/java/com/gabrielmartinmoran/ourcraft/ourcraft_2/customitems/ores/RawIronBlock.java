package com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.ores;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.Main;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.CustomItem;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.CustomItemsModelData;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class RawIronBlock extends CustomItem {

    @Override
    public ItemStack getItem() {
        ItemStack item = new ItemStack(Material.SUGAR, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("Bloque de hierro crudo");
        meta.setCustomModelData(CustomItemsModelData.RAW_IRON_BLOCK.getModelData());
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public Recipe getRecipe() {
        NamespacedKey nsKey = new NamespacedKey(JavaPlugin.getPlugin(Main.class),"raw_iron_block_from_raw_iron");
        ShapelessRecipe recipe = new ShapelessRecipe(nsKey, this.getItem());
        RecipeChoice.ExactChoice raw = new RecipeChoice.ExactChoice(new RawIron().getItem());
        for (int i = 0; i < 9; i++) recipe.addIngredient(raw);
        return recipe;
    }
}
