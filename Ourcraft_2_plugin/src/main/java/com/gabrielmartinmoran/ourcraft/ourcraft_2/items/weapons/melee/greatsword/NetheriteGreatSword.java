package com.gabrielmartinmoran.ourcraft.ourcraft_2.items.weapons.melee.greatsword;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.Main;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.CustomItemsModelData;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.SmithingRecipe;
import org.bukkit.plugin.java.JavaPlugin;

public class NetheriteGreatSword extends BaseGreatSword {
    @Override
    public Recipe getRecipe() {
        NamespacedKey nsKey = new NamespacedKey(JavaPlugin.getPlugin(Main.class),"netherite_greatsword");
        SmithingRecipe recipe = new SmithingRecipe(nsKey, getItem(), new RecipeChoice.ExactChoice(new DiamondGreatSword().getItem()), new RecipeChoice.MaterialChoice(Material.NETHERITE_INGOT));
        return recipe;
    }

    @Override
    public Material getBaseMaterial() {
        return Material.NETHERITE_SWORD;
    }

    @Override
    public String getName() {
        return "Espadón de netherita";
    }

    @Override
    public CustomItemsModelData getModelData() {
        return CustomItemsModelData.NETHERITE_GREATSWORD;
    }

    @Override
    public float getDamage() {
        return 10;
    }
}
