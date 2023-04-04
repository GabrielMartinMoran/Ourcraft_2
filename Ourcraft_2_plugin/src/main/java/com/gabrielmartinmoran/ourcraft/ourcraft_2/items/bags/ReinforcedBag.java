package com.gabrielmartinmoran.ourcraft.ourcraft_2.items.bags;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.Main;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.CustomItem;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.CustomItemsModelData;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.ReinforcedLeather;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.ReinforcedString;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.inventories.NBTInventory;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public class ReinforcedBag extends CustomItem {

    private final int BAG_SIZE = 9;


    @Override
    public ItemStack getItem() {
        ItemStack item = new ItemStack(Material.WARPED_FUNGUS_ON_A_STICK, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("Bolsa de cuero reforzado");
        meta.setLore(Arrays.asList("Una bolsa reforzada para guardar items"));
        item.setItemMeta(meta);
        NBTItem nbt = new NBTItem(item);
        nbt.setInteger("CustomModelData", CustomItemsModelData.REINFORCED_BAG.getModelData());
        nbt.setBoolean("isBag", true);
        item.setItemMeta(nbt.getItem().getItemMeta());
        NBTInventory.prepareItem(item, BAG_SIZE);
        return item;
    }

    @Override
    public Recipe getRecipe() {
        NamespacedKey nsKey = new NamespacedKey(JavaPlugin.getPlugin(Main.class), "reinforced_bag");
        ShapedRecipe recipe = new ShapedRecipe(nsKey, getItem());
        RecipeChoice.ExactChoice reinforcedString = new RecipeChoice.ExactChoice((new ReinforcedString()).getItem());
        RecipeChoice.ExactChoice reinforcedLeather = new RecipeChoice.ExactChoice((new ReinforcedLeather()).getItem());
        recipe.shape("sls", "l_l", "lll");
        recipe.setIngredient('_', Material.AIR);
        recipe.setIngredient('l', reinforcedLeather);
        recipe.setIngredient('s', reinforcedString);
        return recipe;
    }
}
