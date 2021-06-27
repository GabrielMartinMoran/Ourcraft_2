package com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.spells.SpellTypes;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

import java.util.ArrayList;
import java.util.List;

public abstract class CustomItem {

    public abstract ItemStack getItem();

    public Recipe getRecipe() {
        return null;
    }

    public List<Recipe> getRecipes() {
        return new ArrayList<Recipe>();
    }
}
