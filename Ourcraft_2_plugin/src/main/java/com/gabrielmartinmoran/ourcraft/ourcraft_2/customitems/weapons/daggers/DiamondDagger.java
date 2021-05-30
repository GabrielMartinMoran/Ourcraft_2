package com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.weapons.daggers;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.Main;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.CustomItemsModelData;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;

public class DiamongDagger extends BaseDagger {
    @Override
    public Recipe getRecipe() {
        NamespacedKey nsKey = new NamespacedKey(JavaPlugin.getPlugin(Main.class),"diamond_dagger");
        ShapedRecipe recipe = new ShapedRecipe(nsKey, getItem());
        recipe.shape("___","_d_","_s_");
        recipe.setIngredient('_', Material.AIR);
        recipe.setIngredient('d', Material.DIAMOND);
        recipe.setIngredient('s', Material.STICK);
        return recipe;
    }

    @Override
    public Material getMaterial() {
        return Material.DIAMOND_SWORD
    }

    @Override
    public String getName() {
        return "Daga de diamante";
    }

    @Override
    public CustomItemsModelData getModelData() {
        return CustomItemsModelData.DIAMOND_DAGGER;
    }

    @Override
    public int getDamage() {
        return 5;
    }
}
