package com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.spells;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.Main;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.CustomItem;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.CustomItemsModelData;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.MagicEssence;
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

public class SpellScroll extends CustomItem {

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
        ItemStack result = this.getItem();
        result.setAmount(6);
        RecipeChoice.ExactChoice spellbook = new RecipeChoice.ExactChoice((new SpellBook(spellType, spellLevel)).getItem());
        NamespacedKey nsKey = new NamespacedKey(JavaPlugin.getPlugin(Main.class), getNamespaceKey());
        ShapedRecipe recipe = new ShapedRecipe(nsKey, result);
        RecipeChoice.ExactChoice magicEssence = new RecipeChoice.ExactChoice((new MagicEssence()).getItem());
        recipe.shape("bfp","epp","ppp");
        recipe.setIngredient('f', Material.FEATHER);
        recipe.setIngredient('e', magicEssence);
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
