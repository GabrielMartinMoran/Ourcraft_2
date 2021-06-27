package com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.bags;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.Main;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.CustomItem;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.CustomItemsModelData;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.spells.SpellTypes;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Bag extends CustomItem {

    private final int BAG_SIZE = 3;


    @Override
    public ItemStack getItem() {
        ItemStack item = new ItemStack(Material.CARROT_ON_A_STICK, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("Bolsa de cuero");
        meta.setLore(Arrays.asList("Una bolsa para guardar items"));
        item.setItemMeta(meta);
        NBTItem nbt = new NBTItem(item);
        nbt.setInteger("CustomModelData", CustomItemsModelData.BAG.getModelData());
        nbt.setBoolean("isBag", true);
        nbt.setInteger("bagSize", BAG_SIZE);
        return nbt.getItem();
    }

    @Override
    public Recipe getRecipe() {
        NamespacedKey nsKey = new NamespacedKey(JavaPlugin.getPlugin(Main.class),"bag");
        ShapedRecipe recipe = new ShapedRecipe(nsKey, getItem());
        recipe.shape("sls","l_l","lll");
        recipe.setIngredient('_', Material.AIR);
        recipe.setIngredient('l', Material.LEATHER);
        recipe.setIngredient('s', Material.STRING);
        return recipe;
    }
}
