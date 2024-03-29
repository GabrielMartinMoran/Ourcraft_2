package com.gabrielmartinmoran.ourcraft.ourcraft_2.items.weapons.ranged.bows;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.Main;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.CustomItemsModelData;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;

public class BambooBow extends BaseBow {

    @Override
    public Recipe getRecipe() {
        NamespacedKey nsKey = new NamespacedKey(JavaPlugin.getPlugin(Main.class),"bamboo_bow");
        ShapedRecipe recipe = new ShapedRecipe(nsKey, getItem());
        recipe.shape("_bs","b_s","_bs");
        recipe.setIngredient('_', Material.AIR);
        recipe.setIngredient('b', Material.BAMBOO);
        recipe.setIngredient('s', Material.STRING);
        return recipe;
    }

    @Override
    public String getName() {
        return "Arco de bamboo";
    }

    @Override
    public CustomItemsModelData getModelData() {
        return CustomItemsModelData.BAMBOO_BOW;
    }

    @Override
    public float getProjectileVelocityModifier() {
        return 0.7f;
    }
}
