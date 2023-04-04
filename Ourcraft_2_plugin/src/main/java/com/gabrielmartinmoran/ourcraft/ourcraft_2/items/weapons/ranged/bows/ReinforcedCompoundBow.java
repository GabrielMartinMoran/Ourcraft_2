package com.gabrielmartinmoran.ourcraft.ourcraft_2.items.weapons.ranged.bows;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.Main;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.CustomItemsModelData;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.ReinforcedString;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;

public class ReinforcedCompoundBow extends BaseBow {

    @Override
    public Recipe getRecipe() {
        NamespacedKey nsKey = new NamespacedKey(JavaPlugin.getPlugin(Main.class),"reinforced_compound_bow");
        ShapedRecipe recipe = new ShapedRecipe(nsKey, getItem());
        RecipeChoice.ExactChoice reinforcedString = new RecipeChoice.ExactChoice((new ReinforcedString()).getItem());
        recipe.shape("did","i_s","dss");
        recipe.setIngredient('_', Material.AIR);
        recipe.setIngredient('i', Material.IRON_INGOT);
        recipe.setIngredient('d', Material.DIAMOND);
        recipe.setIngredient('s', reinforcedString);
        return recipe;
    }

    @Override
    public String getName() {
        return "Arco compuesto reforzado";
    }

    @Override
    public CustomItemsModelData getModelData() {
        return CustomItemsModelData.REINFORCED_COMPOUND_BOW;
    }

    @Override
    public float getProjectileVelocityModifier() {
        return 1.8f;
    }
}
