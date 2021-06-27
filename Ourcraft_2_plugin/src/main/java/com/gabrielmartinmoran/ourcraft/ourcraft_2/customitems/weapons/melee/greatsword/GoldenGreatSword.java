package com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.weapons.melee.greatsword;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.Main;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.CustomItemsModelData;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;

public class GoldenGreatSword extends BaseGreatSword {
    @Override
    public Recipe getRecipe() {
        NamespacedKey nsKey = new NamespacedKey(JavaPlugin.getPlugin(Main.class),"golden_greatsword");
        ShapedRecipe recipe = new ShapedRecipe(nsKey, getItem());
        recipe.shape("__g","gg_","sg_");
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
        return "Espadón de oro";
    }

    @Override
    public CustomItemsModelData getModelData() {
        return CustomItemsModelData.GOLDEN_GREATSWORD;
    }

    @Override
    public float getDamage() {
        return 6;
    }
}
