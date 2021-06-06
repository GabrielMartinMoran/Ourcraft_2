package com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.weapons.melee.daggers;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.Main;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.CustomItemsModelData;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;

public class GoldenDagger extends BaseDagger {
    @Override
    public Recipe getRecipe() {
        NamespacedKey nsKey = new NamespacedKey(JavaPlugin.getPlugin(Main.class),"golden_dagger");
        ShapedRecipe recipe = new ShapedRecipe(nsKey, getItem());
        recipe.shape("___","_g_","_s_");
        recipe.setIngredient('_', Material.AIR);
        recipe.setIngredient('g', Material.GOLD_INGOT);
        recipe.setIngredient('s', Material.STICK);
        return recipe;
    }

    @Override
    public Material getBaseMaterial() {
        return Material.GOLDEN_SWORD;
    }

    @Override
    public String getName() {
        return "Daga de oro";
    }

    @Override
    public CustomItemsModelData getModelData() {
        return CustomItemsModelData.GOLDEN_DAGGER;
    }

    @Override
    public float getDamage() {
        return 2;
    }
}
