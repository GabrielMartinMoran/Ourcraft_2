package com.gabrielmartinmoran.ourcraft.ourcraft_2.items.weapons.melee.daggers;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.Main;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.CustomItemsModelData;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.*;
import org.bukkit.plugin.java.JavaPlugin;

public class NetheriteDagger extends BaseDagger {
    @Override
    public Recipe getRecipe() {
        NamespacedKey nsKey = new NamespacedKey(JavaPlugin.getPlugin(Main.class),"netherite_dagger");
        SmithingRecipe recipe = new SmithingRecipe(nsKey, getItem(), new RecipeChoice.ExactChoice(new DiamondDagger().getItem()), new RecipeChoice.MaterialChoice(Material.NETHERITE_INGOT));
        return recipe;
    }

    @Override
    public Material getBaseMaterial() {
        return Material.NETHERITE_SWORD;
    }

    @Override
    public String getName() {
        return "Daga de netherita";
    }

    @Override
    public CustomItemsModelData getModelData() {
        return CustomItemsModelData.NETHERITE_DAGGER;
    }

    @Override
    public float getDamage() {
        return 6;
    }
}
