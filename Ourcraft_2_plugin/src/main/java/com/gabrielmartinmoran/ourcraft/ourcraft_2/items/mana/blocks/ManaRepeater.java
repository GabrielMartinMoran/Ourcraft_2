package com.gabrielmartinmoran.ourcraft.ourcraft_2.items.mana.blocks;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.Main;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.CustomItem;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.CustomItemsModelData;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.MagicEssence;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.ManaEssence;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class ManaRepeater extends CustomItem {

    @Override
    public ItemStack getItem() {
        ItemStack item = new ItemStack(Material.NETHER_BRICK_FENCE, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("Repetidor de maná");
        meta.setCustomModelData(CustomItemsModelData.MANA_REPEATER.getModelData());
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public Recipe getRecipe() {
        NamespacedKey nsKey = new NamespacedKey(JavaPlugin.getPlugin(Main.class), "mana_repeater");
        ShapedRecipe recipe = new ShapedRecipe(nsKey, getItem());
        RecipeChoice.ExactChoice manaEssence = new RecipeChoice.ExactChoice((new ManaEssence()).getItem());
        RecipeChoice.ExactChoice magicEssence = new RecipeChoice.ExactChoice((new MagicEssence()).getItem());
        recipe.shape("glg", "imi", "gkg");
        recipe.setIngredient('m', manaEssence);
        recipe.setIngredient('k', magicEssence);
        recipe.setIngredient('l', Material.LIGHTNING_ROD);
        recipe.setIngredient('g', Material.GOLD_INGOT);
        recipe.setIngredient('i', Material.IRON_INGOT);
        return recipe;
    }
}
