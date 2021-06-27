package com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.weapons.melee.greatsword;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.Main;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.CustomItemsModelData;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;

public class IronGreatSword extends BaseGreatSword {
    @Override
    public Recipe getRecipe() {
        NamespacedKey nsKey = new NamespacedKey(JavaPlugin.getPlugin(Main.class),"iron_greatsword");
        ShapedRecipe recipe = new ShapedRecipe(nsKey, getItem());
        recipe.shape("__i","ii_","si_");
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
        return "Espadón de hierro";
    }

    @Override
    public CustomItemsModelData getModelData() {
        return CustomItemsModelData.IRON_GREATSWORD;
    }

    @Override
    public float getDamage() {
        return 8;
    }
}
