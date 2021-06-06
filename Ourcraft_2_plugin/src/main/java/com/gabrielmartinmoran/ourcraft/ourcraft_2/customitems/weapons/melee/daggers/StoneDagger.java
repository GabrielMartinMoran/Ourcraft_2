package com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.weapons.melee.daggers;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.Main;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.CustomItemsModelData;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public class StoneDagger extends BaseDagger {
    @Override
    public Recipe getRecipe() {
        NamespacedKey nsKey = new NamespacedKey(JavaPlugin.getPlugin(Main.class),"stone_dagger");
        ShapedRecipe recipe = new ShapedRecipe(nsKey, getItem());
        recipe.shape("___","_c_","_s_");
        recipe.setIngredient('_', Material.AIR);
        recipe.setIngredient('c', new RecipeChoice.MaterialChoice(Arrays.asList(Material.COBBLESTONE, Material.BLACKSTONE)));
        recipe.setIngredient('s', Material.STICK);
        return recipe;
    }

    @Override
    public Material getBaseMaterial() {
        return Material.STONE_SWORD;
    }

    @Override
    public String getName() {
        return "Daga de piedra";
    }

    @Override
    public CustomItemsModelData getModelData() {
        return CustomItemsModelData.STONE_DAGGER;
    }

    @Override
    public float getDamage() {
        return 3;
    }
}
