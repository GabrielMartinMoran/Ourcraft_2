package com.gabrielmartinmoran.ourcraft.ourcraft_2.items.mana.buffs;

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

import java.util.Arrays;

public class PowerTablet extends CustomItem {
    @Override
    public ItemStack getItem() {
        ItemStack item = new ItemStack(Material.CARROT_ON_A_STICK, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("Tablilla de poder");
        meta.setLore(Arrays.asList("Aumenta el consumo de maná pero también el poder"));
        meta.setCustomModelData(CustomItemsModelData.POWER_TABLET.getModelData());
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public Recipe getRecipe() {
        NamespacedKey nsKey = new NamespacedKey(JavaPlugin.getPlugin(Main.class), "power_tablet");
        ShapedRecipe recipe = new ShapedRecipe(nsKey, getItem());
        RecipeChoice.ExactChoice manaEssence = new RecipeChoice.ExactChoice((new ManaEssence()).getItem());
        RecipeChoice.ExactChoice magicEssence = new RecipeChoice.ExactChoice((new MagicEssence()).getItem());
        recipe.shape("www", "elm", "www");
        recipe.setIngredient('w', Material.COBBLED_DEEPSLATE_WALL);
        recipe.setIngredient('e', manaEssence);
        recipe.setIngredient('m', magicEssence);
        recipe.setIngredient('l', Material.LIGHTNING_ROD);
        return recipe;
    }
}
