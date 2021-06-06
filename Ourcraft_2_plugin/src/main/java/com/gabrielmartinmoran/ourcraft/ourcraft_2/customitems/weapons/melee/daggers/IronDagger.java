package com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.weapons.melee.daggers;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.Main;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.CustomItemsModelData;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;

public class IronDagger extends BaseDagger {
    @Override
    public Recipe getRecipe() {
        NamespacedKey nsKey = new NamespacedKey(JavaPlugin.getPlugin(Main.class),"iron_dagger");
        ShapedRecipe recipe = new ShapedRecipe(nsKey, getItem());
        recipe.shape("___","_i_","_s_");
        recipe.setIngredient('_', Material.AIR);
        recipe.setIngredient('i', Material.IRON_INGOT);
        recipe.setIngredient('s', Material.STICK);
        return recipe;
    }

    @Override
    public Material getBaseMaterial() {
        return Material.IRON_SWORD;
    }

    @Override
    public String getName() {
        return "Daga de hierro";
    }

    @Override
    public CustomItemsModelData getModelData() {
        return CustomItemsModelData.IRON_DAGGER;
    }

    @Override
    public float getDamage() {
        return 4;
    }
}
