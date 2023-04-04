package com.gabrielmartinmoran.ourcraft.ourcraft_2.items.mana.potions;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.Main;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.ManaEssence;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.plugin.java.JavaPlugin;

public class RegularManaPotion extends BaseManaPotion {

    @Override
    public Recipe getRecipe() {
        NamespacedKey nsKey = new NamespacedKey(JavaPlugin.getPlugin(Main.class),"regular_mana_potion");
        ShapelessRecipe recipe = new ShapelessRecipe(nsKey, getItem());
        RecipeChoice.ExactChoice manaEssence = new RecipeChoice.ExactChoice((new ManaEssence()).getItem());
        recipe.addIngredient(Material.GLASS_BOTTLE);
        for (int i = 0; i < 2; i++) recipe.addIngredient(manaEssence);
        return recipe;
    }

    @Override
    protected String getName() {
        return "Poción de maná regular";
    }

    @Override
    protected int getManaRecoverAmount() {
        return 50;
    }
}
