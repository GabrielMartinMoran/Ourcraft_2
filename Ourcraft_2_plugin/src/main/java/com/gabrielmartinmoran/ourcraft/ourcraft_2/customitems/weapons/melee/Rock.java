package com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.weapons.melee;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.Main;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.CustomItemsModelData;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.weapons.melee.MeleeWeapon;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.weapons.melee.MeleeWeaponCharacteristics;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.List;

public class Rock extends MeleeWeapon {
    @Override
    public Recipe getRecipe() {
        NamespacedKey nsKey = new NamespacedKey(JavaPlugin.getPlugin(Main.class),"rock");
        ShapelessRecipe recipe = new ShapelessRecipe(nsKey, getItem());
        recipe.addIngredient(new RecipeChoice.MaterialChoice(Arrays.asList(Material.COBBLESTONE, Material.BLACKSTONE)));
        return recipe;
    }

    @Override
    public float getAttackSpeed() {
        return 1f;
    }

    @Override
    public Material getBaseMaterial() {
        return Material.GOLDEN_PICKAXE;
    }

    @Override
    public float getKnockback() {
        return -0.5f;
    }

    @Override
    public String getName() {
        return "Roca";
    }

    @Override
    public CustomItemsModelData getModelData() {
        return CustomItemsModelData.ROCK;
    }

    @Override
    public float getDamage() {
        return 2;
    }

    @Override
    public float getThrowingDamage() {
        return 1;
    }

    @Override
    public List<MeleeWeaponCharacteristics> getCharacteristics() {
        return Arrays.asList(MeleeWeaponCharacteristics.THROWABE, MeleeWeaponCharacteristics.TWO_HANDED, MeleeWeaponCharacteristics.IMPRACTICAL);
    }
}
