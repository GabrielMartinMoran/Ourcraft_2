package com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.potions;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.Main;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.CustomItem;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.ManaEssence;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.ReinforcedString;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class ManaPotion extends BaseManaPotion {

    @Override
    public Recipe getRecipe() {
        NamespacedKey nsKey = new NamespacedKey(JavaPlugin.getPlugin(Main.class),"mana_potion");
        ShapelessRecipe recipe = new ShapelessRecipe(nsKey, getItem());
        RecipeChoice.ExactChoice manaEssence = new RecipeChoice.ExactChoice((new ManaEssence()).getItem());
        recipe.addIngredient(Material.GLASS_BOTTLE);
        recipe.addIngredient(manaEssence);
        return recipe;
    }

    @Override
    protected String getName() {
        return "Poción de maná";
    }

    @Override
    protected int getManaRecoverAmount() {
        return 20;
    }
}
