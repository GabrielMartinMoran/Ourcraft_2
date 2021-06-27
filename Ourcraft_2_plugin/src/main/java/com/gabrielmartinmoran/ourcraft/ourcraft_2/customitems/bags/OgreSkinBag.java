package com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.bags;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.Main;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.*;
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

public class OgreSkinBag extends CustomItem {

    private final int BAG_SIZE = 18;


    @Override
    public ItemStack getItem() {
        ItemStack item = new ItemStack(Material.WARPED_FUNGUS_ON_A_STICK, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("Bolsa de piel de ogro");
        meta.setLore(Arrays.asList("Una bolsa hecha de piel de ogro para guardar items"));
        item.setItemMeta(meta);
        NBTItem nbt = new NBTItem(item);
        nbt.setInteger("CustomModelData", CustomItemsModelData.OGRE_SKIN_BAG.getModelData());
        nbt.setBoolean("isBag", true);
        nbt.setInteger("bagSize", BAG_SIZE);
        return nbt.getItem();
    }

    @Override
    public Recipe getRecipe() {
        NamespacedKey nsKey = new NamespacedKey(JavaPlugin.getPlugin(Main.class),"ogre_skin_bag");
        ShapedRecipe recipe = new ShapedRecipe(nsKey, getItem());
        RecipeChoice.ExactChoice reinforcedString = new RecipeChoice.ExactChoice((new ReinforcedString()).getItem());
        RecipeChoice.ExactChoice ogreSkin = new RecipeChoice.ExactChoice((new OgreSkin()).getItem());
        recipe.shape("sls","l_l","lll");
        recipe.setIngredient('_', Material.AIR);
        recipe.setIngredient('l', ogreSkin);
        recipe.setIngredient('s', reinforcedString);
        return recipe;
    }
}
