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

public class StoneGreatSword extends BaseGreatSword {
    @Override
    public Recipe getRecipe() {
        NamespacedKey nsKey = new NamespacedKey(JavaPlugin.getPlugin(Main.class),"stone_greatsword");
        ShapedRecipe recipe = new ShapedRecipe(nsKey, getItem());
        recipe.shape("__c","cc_","sc_");
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
        return "Espadón de piedra";
    }

    @Override
    public CustomItemsModelData getModelData() {
        return CustomItemsModelData.STONE_GREATSWORD;
    }

    @Override
    public float getDamage() {
        return 7;
    }
}
