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

public class ManaCharger extends CustomItem {

    @Override
    public ItemStack getItem() {
        ItemStack item = new ItemStack(Material.SMOOTH_STONE, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("Cargador de maná");
        meta.setCustomModelData(CustomItemsModelData.MANA_CHARGER.getModelData());
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public Recipe getRecipe() {
        NamespacedKey nsKey = new NamespacedKey(JavaPlugin.getPlugin(Main.class), "mana_charger");
        ShapedRecipe recipe = new ShapedRecipe(nsKey, getItem());
        RecipeChoice.ExactChoice manaEssence = new RecipeChoice.ExactChoice((new ManaEssence()).getItem());
        RecipeChoice.ExactChoice magicEssence = new RecipeChoice.ExactChoice((new MagicEssence()).getItem());
        recipe.shape("cac", "gmg", "iki");
        recipe.setIngredient('m', manaEssence);
        recipe.setIngredient('k', magicEssence);
        recipe.setIngredient('g', Material.GOLD_INGOT);
        recipe.setIngredient('a', Material.AMETHYST_SHARD);
        recipe.setIngredient('i', Material.IRON_INGOT);
        recipe.setIngredient('c', Material.COPPER_INGOT);
        return recipe;
    }
}
