package com.gabrielmartinmoran.ourcraft.ourcraft_2.items.weapons.melee.greatsword;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.Main;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.CustomItemsModelData;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public class WoodenGreatSword extends BaseGreatSword {
    @Override
    public Recipe getRecipe() {
        NamespacedKey nsKey = new NamespacedKey(JavaPlugin.getPlugin(Main.class),"wooden_greatsword");
        ShapedRecipe recipe = new ShapedRecipe(nsKey, getItem());
        recipe.shape("__p","pp_","sp_");
        recipe.setIngredient('_', Material.AIR);
        recipe.setIngredient('p', new RecipeChoice.MaterialChoice(Arrays.asList(
                Material.ACACIA_PLANKS, Material.BIRCH_PLANKS, Material.CRIMSON_PLANKS,
                Material.DARK_OAK_PLANKS, Material.OAK_PLANKS, Material.JUNGLE_PLANKS,
                Material.SPRUCE_PLANKS, Material.WARPED_PLANKS
        )));
        recipe.setIngredient('s', Material.STICK);
        return recipe;
    }

    @Override
    public Material getBaseMaterial() {
        return Material.WOODEN_SWORD;
    }

    @Override
    public String getName() {
        return "Espadón de madera";
    }

    @Override
    public CustomItemsModelData getModelData() {
        return CustomItemsModelData.WOODEN_GREATSWORD;
    }

    @Override
    public float getDamage() {
        return 6;
    }
}
