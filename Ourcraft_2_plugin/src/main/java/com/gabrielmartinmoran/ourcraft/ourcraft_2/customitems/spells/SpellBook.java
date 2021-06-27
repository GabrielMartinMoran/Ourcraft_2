package com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.spells;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.Main;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.CustomItem;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.CustomItemsModelData;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.MagicEssence;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.ManaEssence;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.spells.SpellTypes;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.utils.SpellsUtils;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;

public class SpellBook extends CustomItem {


    private SpellTypes spellType;
    private int spellLevel;

    private HashMap<SpellTypes, Material> spellDataMap;

    public SpellBook(SpellTypes spellType, int spellLevel) {
        this.spellType = spellType;
        this.spellLevel = spellLevel;
        this.spellDataMap = new HashMap<SpellTypes, Material>();
        this.spellDataMap.put(SpellTypes.LIGHTNING, Material.GOLD_BLOCK);
        this.spellDataMap.put(SpellTypes.FIREBALL, Material.FIRE_CHARGE);
        this.spellDataMap.put(SpellTypes.NECROMANCER, Material.WITHER_SKELETON_SKULL);
        this.spellDataMap.put(SpellTypes.HEALING, Material.GHAST_TEAR);
        this.spellDataMap.put(SpellTypes.LEVITATE, Material.SHULKER_SHELL);
        this.spellDataMap.put(SpellTypes.TELEPORT, Material.ENDER_EYE);
        this.spellDataMap.put(SpellTypes.SLOW_FALL, Material.PHANTOM_MEMBRANE);
        this.spellDataMap.put(SpellTypes.FIRE_RESISTANCE, Material.MAGMA_CREAM);
        this.spellDataMap.put(SpellTypes.MAGIC_ARROWS, Material.SPECTRAL_ARROW);
        this.spellDataMap.put(SpellTypes.MAGIC_MISSILES, Material.FIREWORK_ROCKET);
        this.spellDataMap.put(SpellTypes.POISON_CLOUD, Material.FERMENTED_SPIDER_EYE);
        this.spellDataMap.put(SpellTypes.MAKE_LEVITATE, Material.PUFFERFISH);
        this.spellDataMap.put(SpellTypes.NAUSEATING_ORB, Material.POISONOUS_POTATO);
        this.spellDataMap.put(SpellTypes.BLINDNESS_ORB, Material.INK_SAC);
        this.spellDataMap.put(SpellTypes.REALENTIZING_ORB, Material.SOUL_SAND);
        this.spellDataMap.put(SpellTypes.EXPLODING_ORB, Material.END_CRYSTAL);
        this.spellDataMap.put(SpellTypes.GOOD_LUCK, Material.RABBIT_FOOT);
        this.spellDataMap.put(SpellTypes.HASTE, Material.DIAMOND_PICKAXE);
        this.spellDataMap.put(SpellTypes.MAGIC_SHOT, Material.LAPIS_BLOCK);
        this.spellDataMap.put(SpellTypes.FIRE_RETARDANT_EFFORD, Material.REDSTONE_BLOCK);
        this.spellDataMap.put(SpellTypes.EXPLOSION, Material.TNT);
        this.spellDataMap.put(SpellTypes.SPEED_UP, Material.GOLDEN_CARROT);
        this.spellDataMap.put(SpellTypes.SPEED_UP_EFFORD, Material.CARROT);
        this.spellDataMap.put(SpellTypes.BURNING_ORB, Material.FLINT_AND_STEEL);
        this.spellDataMap.put(SpellTypes.FAKE_LIFE, Material.GOLDEN_APPLE);
        this.spellDataMap.put(SpellTypes.SELF_HEALING, Material.ENCHANTED_GOLDEN_APPLE);
        this.spellDataMap.put(SpellTypes.CLEAN_STATUSES, Material.MILK_BUCKET);
    }

    @Override
    public ItemStack getItem() {
        ItemStack item = new ItemStack(Material.CARROT_ON_A_STICK, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(getItemName(spellType, spellLevel));
        meta.setLore(getItemLore(spellType));
        item.setItemMeta(meta);
        NBTItem nbt = new NBTItem(item);
        nbt.setInteger("CustomModelData", CustomItemsModelData.SPELLBOOK.getModelData());
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
        RecipeChoice.ExactChoice magicEssence = new RecipeChoice.ExactChoice((new MagicEssence()).getItem());
        RecipeChoice.ExactChoice manaEssence = new RecipeChoice.ExactChoice((new ManaEssence()).getItem());
        recipe.shape("_m_","beb","_m_");
        recipe.setIngredient('_', Material.AIR);
        recipe.setIngredient('b', previousTierChoice);
        recipe.setIngredient('m', manaEssence);
        recipe.setIngredient('e', magicEssence);

        return recipe;
    }

    private Recipe getEmptySpellbookRecipe() {
        NamespacedKey nsKey = new NamespacedKey(JavaPlugin.getPlugin(Main.class), "empty_spellbook");
        ShapedRecipe recipe = new ShapedRecipe(nsKey, getItem());
        RecipeChoice.ExactChoice magicEssence = new RecipeChoice.ExactChoice((new MagicEssence()).getItem());
        RecipeChoice.ExactChoice manaEssence = new RecipeChoice.ExactChoice((new ManaEssence()).getItem());
        recipe.shape("_m_","ebe","_m_");
        recipe.setIngredient('_', Material.AIR);
        recipe.setIngredient('e', magicEssence);
        recipe.setIngredient('m', manaEssence);
        recipe.setIngredient('b', Material.WRITABLE_BOOK);
        return recipe;
    }

    private Recipe getFirstLevelRecipe() {
        RecipeChoice.ExactChoice baseSpellbook = new RecipeChoice.ExactChoice((new SpellBook(SpellTypes.NONE, 0)).getItem());
        Material modifier = this.spellDataMap.get(spellType);
        NamespacedKey nsKey = new NamespacedKey(JavaPlugin.getPlugin(Main.class), getNamespaceKey());
        ShapedRecipe recipe = new ShapedRecipe(nsKey, getItem());
        RecipeChoice.ExactChoice magicEssence = new RecipeChoice.ExactChoice((new MagicEssence()).getItem());
        RecipeChoice.ExactChoice manaEssence = new RecipeChoice.ExactChoice((new ManaEssence()).getItem());
        recipe.shape("_i_","msm","_e_");
        recipe.setIngredient('_', Material.AIR);
        recipe.setIngredient('i', modifier);
        recipe.setIngredient('s', baseSpellbook);
        recipe.setIngredient('m', manaEssence);
        recipe.setIngredient('e', magicEssence);
        return recipe;
    }

    private String getNamespaceKey() {
        return String.format("%d_spellbook_lvl_%d", spellType.getId(), spellLevel);
    }

    private String getItemName(SpellTypes spellType, int level) {
        if (spellType == SpellTypes.NONE) return "Libro de conjuros vacío";
        String formatter = "" + ChatColor.GOLD + "Libro de conjuros: " + spellType.getNameColor() + "%s" + ChatColor.GOLD + " nivel " + ChatColor.GREEN + "%d";
        return String.format(formatter, spellType.getName(), level);
    }

    private ArrayList<String> getItemLore(SpellTypes spellType) {
        ArrayList<String> lores = new ArrayList<String>();
        if (spellType == SpellTypes.NONE) {
            lores.add(getLoreLine("Utiliza este libro de conjuros vacío para crear"));
            lores.add(getLoreLine("libros de conjuro especializados"));
        } else {
            for (String line: spellType.getLore().split("\n")) {
                lores.add(getLoreLine(line));
            }
            lores.add("" + ChatColor.GREEN + ChatColor.ITALIC + "Costo de maná: " + ChatColor.AQUA + ChatColor.ITALIC + SpellsUtils.calculateSpellBookManaCost(spellType, spellLevel));
        }
        return lores;
    }

    private String getLoreLine(String line) {
        return "" + ChatColor.DARK_PURPLE + line;
    }
}
