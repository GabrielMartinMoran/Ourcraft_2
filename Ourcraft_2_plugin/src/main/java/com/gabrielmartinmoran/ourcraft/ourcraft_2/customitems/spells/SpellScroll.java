package com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.Main;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.spells.SpellTypes;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.utils.SpellsUtils;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public class SpellScroll implements CustomItem {

    private SpellTypes spellType;
    private int spellLevel;

    public SpellScroll(SpellTypes spellType, int spellLevel) {
        this.spellType = spellType;
        this.spellLevel = spellLevel;
    }

    @Override
    public ItemStack getItem() {
        ItemStack item = new ItemStack(Material.PAPER, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(getItemName(spellType, spellLevel));
        meta.setLore(getItemLore(spellType));
        item.setItemMeta(meta);
        NBTItem nbt = new NBTItem(item);
        nbt.setInteger("CustomModelData", CustomItemsModelData.SPELL_SCROLL.getModelData());
        if(spellType != SpellTypes.NONE) {
            nbt.setInteger("spellType", spellType.getId());
            nbt.setInteger("spellLevel", spellLevel);
        }
        return nbt.getItem();
    }

    @Override
    public Recipe getRecipe() {
        RecipeChoice.ExactChoice spellbook = new RecipeChoice.ExactChoice((new SpellBook(spellType, spellLevel)).getItem());
        NamespacedKey nsKey = new NamespacedKey(JavaPlugin.getPlugin(Main.class), getNamespaceKey());
        ShapedRecipe recipe = new ShapedRecipe(nsKey, getItem());
        recipe.shape("_f_","bip","_d_");
        recipe.setIngredient('_', Material.AIR);
        recipe.setIngredient('f', Material.FEATHER);
        recipe.setIngredient('i', Material.INK_SAC);
        recipe.setIngredient('d', Material.DIAMOND);
        recipe.setIngredient('p', Material.PAPER);
        recipe.setIngredient('b', spellbook);
        return recipe;
    }

    private String getItemName(SpellTypes spellType, int level) {
        String formatter = "" + ChatColor.GOLD + "Pergamino de conjuro: " + spellType.getNameColor() + "%s" + ChatColor.GOLD + " nivel " + ChatColor.GREEN + "%d";
        return String.format(formatter, spellType.getName(), level);
    }

    private ArrayList<String> getItemLore(SpellTypes spellType) {
        ArrayList<String> lores = new ArrayList<String>();
        for (String line: spellType.getLore().split("\n")) {
            lores.add(getLoreLine(line));
        }
        lores.add("" + ChatColor.GREEN + ChatColor.ITALIC + "Item de 1 solo uso");
        return lores;
    }

    private String getLoreLine(String line) {
        return "" + ChatColor.DARK_PURPLE + line;
    }

    private String getNamespaceKey() {
        return String.format("%d_spellscroll_lvl_%d", spellType.getId(), spellLevel);
    }
}
