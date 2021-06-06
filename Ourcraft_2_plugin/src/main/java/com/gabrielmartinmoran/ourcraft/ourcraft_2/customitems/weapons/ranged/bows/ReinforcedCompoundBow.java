package com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.weapons.ranged.bows;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.Main;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.CustomItemsModelData;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.ReinforcedString;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public class CompoundBow extends BaseBow {

    @Override
    public Recipe getRecipe() {
        NamespacedKey nsKey = new NamespacedKey(JavaPlugin.getPlugin(Main.class),"reinforced_wooden_bow");
        ShapedRecipe recipe = new ShapedRecipe(nsKey, getItem());
        RecipeChoice.ExactChoice reinforcedString = new RecipeChoice.ExactChoice((new ReinforcedString()).getItem());
        recipe.shape("ipi","p_s","iss");
        recipe.setIngredient('_', Material.AIR);
        recipe.setIngredient('s', Material.IRON_INGOT);
        recipe.setIngredient('p', new RecipeChoice.MaterialChoice(Arrays.asList(
                Material.ACACIA_PLANKS, Material.BIRCH_PLANKS, Material.CRIMSON_PLANKS,
                Material.DARK_OAK_PLANKS, Material.OAK_PLANKS, Material.JUNGLE_PLANKS,
                Material.SPRUCE_PLANKS, Material.WARPED_PLANKS
        )));
        recipe.setIngredient('s', reinforcedString);
        return recipe;
    }

    @Override
    public String getName() {
        return "Arco de madera reforzado";
    }

    @Override
    public CustomItemsModelData getModelData() {
        return CustomItemsModelData.COMPOUND_BOW;
    }

    @Override
    public float getProjectileVelocityModifier() {
        return 1.4f;
    }
}
