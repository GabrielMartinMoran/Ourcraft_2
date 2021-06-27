package com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.ores;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.Main;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.CustomItem;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.CustomItemsModelData;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.List;

public class RawIron extends CustomItem {

    @Override
    public ItemStack getItem() {
        ItemStack item = new ItemStack(Material.SUGAR, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("Hierro crudo");
        meta.setCustomModelData(CustomItemsModelData.RAW_IRON.getModelData());
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public Recipe getRecipe() {
        NamespacedKey nsKey = new NamespacedKey(JavaPlugin.getPlugin(Main.class),"raw_iron_from_raw_iron_block");
        ItemStack item = this.getItem();
        item.setAmount(9);
        ShapelessRecipe recipe = new ShapelessRecipe(nsKey, item);
        RecipeChoice.ExactChoice rawBlock = new RecipeChoice.ExactChoice(new RawIronBlock().getItem());
        recipe.addIngredient(rawBlock);
        return recipe;
    }
}
