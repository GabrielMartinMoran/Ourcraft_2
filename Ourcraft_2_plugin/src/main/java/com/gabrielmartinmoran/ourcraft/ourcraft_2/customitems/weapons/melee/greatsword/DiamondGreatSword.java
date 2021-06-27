package com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.weapons.melee.greatsword;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.Main;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.CustomItemsModelData;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;

public class DiamondGreatSword extends BaseGreatSword {
    @Override
    public Recipe getRecipe() {
        NamespacedKey nsKey = new NamespacedKey(JavaPlugin.getPlugin(Main.class),"diamond_greatsword");
        ShapedRecipe recipe = new ShapedRecipe(nsKey, getItem());
        recipe.shape("__d","dd_","sd_");
        recipe.setIngredient('_', Material.AIR);
        recipe.setIngredient('d', Material.DIAMOND);
        recipe.setIngredient('s', Material.STICK);
        return recipe;
    }

    @Override
    public Material getBaseMaterial() {
        return Material.DIAMOND_SWORD;
    }

    @Override
    public String getName() {
        return "Espadón de diamante";
    }

    @Override
    public CustomItemsModelData getModelData() {
        return CustomItemsModelData.DIAMOND_GREATSWORD;
    }

    @Override
    public float getDamage() { return 9; }
}
