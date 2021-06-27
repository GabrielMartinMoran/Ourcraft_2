package com.gabrielmartinmoran.ourcraft.ourcraft_2.utils;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.*;

public class RecipesUtils {

    public static NamespacedKey getRecipeNamespacedKey(Recipe recipe) {
        if (recipe instanceof ShapedRecipe) return ((ShapedRecipe) recipe).getKey();
        if (recipe instanceof ShapelessRecipe) return ((ShapelessRecipe) recipe).getKey();
        if (recipe instanceof SmithingRecipe) return ((SmithingRecipe) recipe).getKey();
        if (recipe instanceof SmokingRecipe) return ((SmokingRecipe) recipe).getKey();
        if (recipe instanceof StonecuttingRecipe) return ((StonecuttingRecipe) recipe).getKey();
        if (recipe instanceof FurnaceRecipe) return ((FurnaceRecipe) recipe).getKey();
        if (recipe instanceof CampfireRecipe) return ((CampfireRecipe) recipe).getKey();
        if (recipe instanceof BlastingRecipe) return ((BlastingRecipe) recipe).getKey();
        if (recipe instanceof ComplexRecipe) return ((ComplexRecipe) recipe).getKey();
        return null;
    }

    public static String getRecipeName(Recipe recipe) {
        NamespacedKey nsKey = getRecipeNamespacedKey(recipe);
        if(nsKey != null) return nsKey.toString();
        return null;
    }

    /*
    public static String getRecipeName(Recipe recipe) {
        if (recipe instanceof ShapedRecipe) return ((ShapedRecipe) recipe).getKey().toString();
        if (recipe instanceof ShapelessRecipe) return ((ShapelessRecipe) recipe).getKey().toString();
        if (recipe instanceof SmithingRecipe) return ((SmithingRecipe) recipe).getKey().toString();
        if (recipe instanceof SmokingRecipe) return ((SmokingRecipe) recipe).getKey().toString();
        if (recipe instanceof StonecuttingRecipe) return ((StonecuttingRecipe) recipe).getKey().toString();
        if (recipe instanceof FurnaceRecipe) return ((FurnaceRecipe) recipe).getKey().toString();
        if (recipe instanceof CampfireRecipe) return ((CampfireRecipe) recipe).getKey().toString();
        if (recipe instanceof BlastingRecipe) return ((BlastingRecipe) recipe).getKey().toString();
        return null;
    }*/
}
