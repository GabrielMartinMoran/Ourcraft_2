package com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.Main;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.spells.SpellTypes;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SpellBook implements CustomItem {


    private SpellTypes spellType;
    private int spellLevel;

    private final int MODEL_DATA = 100001;

    private class SpellBookData {
        public ChatColor nameColor;
        public String name;
        public String lore;
        public Material creationMaterial;

        public SpellBookData(ChatColor nameColor, String name, String lore, Material creationMaterial) {
            this.nameColor = nameColor;
            this.name = name;
            this.lore = lore;
            this.creationMaterial = creationMaterial;
        }
    }

    private HashMap<SpellTypes, SpellBookData> spellDataMap;

    // Colores: https://codepen.io/0biwan/pen/ggVemP
    public SpellBook(SpellTypes spellType, int spellLevel) {
        this.spellType = spellType;
        this.spellLevel = spellLevel;
        this.spellDataMap = new HashMap<SpellTypes, SpellBookData>();
        this.spellDataMap.put(SpellTypes.LIGHTNING, new SpellBookData(ChatColor.AQUA, "Rayo", "Invoca rayos que caen en el objetivo", Material.GOLD_BLOCK));
        this.spellDataMap.put(SpellTypes.FIREBALL, new SpellBookData(ChatColor.RED, "Bola de fuego", "Invoca una bola de fuego que arremete contra el objetivo", Material.FIRE_CHARGE));
        this.spellDataMap.put(SpellTypes.NECROMANCER, new SpellBookData(ChatColor.DARK_GRAY, "Nigromancia", "Invoca esqueletos wither que atacan al objetivo", Material.WITHER_SKELETON_SKULL));
        this.spellDataMap.put(SpellTypes.HEALING, new SpellBookData(ChatColor.LIGHT_PURPLE, "Curación", "Cura a todos los jugadores que se encuentren a pocos\nbloques de quien lanza el conjuro", Material.GHAST_TEAR));
        this.spellDataMap.put(SpellTypes.LEVITATE, new SpellBookData(ChatColor.WHITE, "Levitar", "Hace levitar en el aire a quien lanza el conjuro", Material.SHULKER_SHELL));
        this.spellDataMap.put(SpellTypes.TELEPORT, new SpellBookData(ChatColor.DARK_PURPLE, "Teletransporte", "Teletransporta al usuario cierta cantidad de\nbloques hacia adelante", Material.ENDER_EYE));
        this.spellDataMap.put(SpellTypes.SLOW_FALL, new SpellBookData(ChatColor.WHITE, "Lenta caída", "Hace caer suavemente a quien lanza el conjuro", Material.PHANTOM_MEMBRANE));
        this.spellDataMap.put(SpellTypes.FIRE_RESISTANCE, new SpellBookData(ChatColor.DARK_RED, "Resistencia ignea", "Hace resistir al fuego a quien lanza el conjuro", Material.MAGMA_CREAM));
        this.spellDataMap.put(SpellTypes.MAGIC_ARROWS, new SpellBookData(ChatColor.YELLOW, "Flechas mágicas", "Lanza flechas mágicas al objetivo", Material.SPECTRAL_ARROW));
        this.spellDataMap.put(SpellTypes.MAGIC_MISSILES, new SpellBookData(ChatColor.BLUE, "Misiles mágicos", "Lanza misiles mágicos que siguen al objetivo", Material.FIREWORK_ROCKET));
        this.spellDataMap.put(SpellTypes.POISON_CLOUD, new SpellBookData(ChatColor.GREEN, "Nube venenosa", "Lanza un proyectil que genera una nube venenosa al impactar", Material.FERMENTED_SPIDER_EYE));
    }

    @Override
    public ItemStack getItem() {
        ItemStack item = new ItemStack(Material.CARROT_ON_A_STICK, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(getItemName(spellType, spellLevel));
        meta.setLore(getItemLore(spellType));
        item.setItemMeta(meta);
        NBTItem nbt = new NBTItem(item);
        nbt.setInteger("CustomModelData", MODEL_DATA);
        if(spellType != SpellTypes.NONE) {
            nbt.setInteger("spellType", spellType.getId());
            nbt.setInteger("spellLevel", spellLevel);
        }
        return nbt.getItem();
    }

    @Override
    public Recipe getRecipe() {
        if (spellType == SpellTypes.NONE) return getEmptySpellbookRecipe();
        if (spellLevel == 1) return getFirstLevelRecipe();
        RecipeChoice.ExactChoice previousTierChoice = new RecipeChoice.ExactChoice((new SpellBook(spellType, spellLevel - 1)).getItem());
        NamespacedKey nsKey = new NamespacedKey(JavaPlugin.getPlugin(Main.class), getNamespaceKey());
        ShapedRecipe recipe = new ShapedRecipe(nsKey, getItem());
        recipe.shape("_g_","brb","_g_");
        recipe.setIngredient('_', Material.AIR);
        recipe.setIngredient('g', Material.GOLD_INGOT);
        recipe.setIngredient('b', previousTierChoice);
        recipe.setIngredient('r', Material.REDSTONE);
        return recipe;
    }

    private Recipe getEmptySpellbookRecipe() {
        NamespacedKey nsKey = new NamespacedKey(JavaPlugin.getPlugin(Main.class), "empty_spellbook");
        ShapedRecipe recipe = new ShapedRecipe(nsKey, getItem());
        recipe.shape("_d_","gbg","_e_");
        recipe.setIngredient('_', Material.AIR);
        recipe.setIngredient('d', Material.DIAMOND);
        recipe.setIngredient('e', Material.EMERALD);
        recipe.setIngredient('g', Material.GOLD_INGOT);
        recipe.setIngredient('b', Material.WRITABLE_BOOK);
        return recipe;
    }

    private Recipe getFirstLevelRecipe() {
        RecipeChoice.ExactChoice baseSpellbook = new RecipeChoice.ExactChoice((new SpellBook(SpellTypes.NONE, 0)).getItem());
        Material modifier = this.spellDataMap.get(spellType).creationMaterial;
        NamespacedKey nsKey = new NamespacedKey(JavaPlugin.getPlugin(Main.class), getNamespaceKey());
        ShapedRecipe recipe = new ShapedRecipe(nsKey, getItem());
        recipe.shape("_m_","dse","_b_");
        recipe.setIngredient('_', Material.AIR);
        recipe.setIngredient('d', Material.DIAMOND);
        recipe.setIngredient('e', Material.EMERALD);
        recipe.setIngredient('m', modifier);
        recipe.setIngredient('s', baseSpellbook);
        recipe.setIngredient('b', Material.ENCHANTED_BOOK);
        return recipe;
    }

    private String getNamespaceKey() {
        return String.format("%d_spellbook_lvl_%d", spellType.getId(), spellLevel);
    }

    private String getItemName(SpellTypes spellType, int level) {
        if (spellType == SpellTypes.NONE) return "Libro de conjuros vacío";
        SpellBookData data = spellDataMap.get(spellType);
        String formatter = "" + ChatColor.GOLD + "Libro de conjuros: " + data.nameColor + "%s" + ChatColor.GOLD + " nivel " + ChatColor.GREEN + "%d";
        return String.format(formatter, data.name, level);
    }

    private ArrayList<String> getItemLore(SpellTypes spellType) {
        ArrayList<String> lores = new ArrayList<String>();
        if (spellType == SpellTypes.NONE) {
            lores.add(getLoreLine("Utiliza este libro de conjuros vacío para crear"));
            lores.add(getLoreLine("libros de conjuro especializados"));
        } else {
            for (String line: spellDataMap.get(spellType).lore.split("\n")) {
                lores.add(getLoreLine(line));
            }
        }
        return lores;
    }

    private String getLoreLine(String line) {
        return "" + ChatColor.DARK_PURPLE + line;
    }
}
