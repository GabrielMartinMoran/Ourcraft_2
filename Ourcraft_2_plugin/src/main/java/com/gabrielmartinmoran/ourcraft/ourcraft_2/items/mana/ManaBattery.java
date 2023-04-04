package com.gabrielmartinmoran.ourcraft.ourcraft_2.items.mana;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.Main;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.CustomItem;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.CustomItemsModelData;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.MagicEssence;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.ManaEssence;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.utils.ManaTextFormatter;
import de.tr7zw.nbtapi.NBTItem;
import jline.internal.Nullable;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public class ManaBattery extends CustomItem {

    public static final String IS_MANA_BATTERY_TAG = "isManaBattery";
    public static final String MAX_MANA_CAPACITY_TAG = "maxManaCapacity";
    public static final String CURRENT_MANA_TAG = "currentMana";

    private final int MAX_MANA_CAPACITY = 1000;

    @Override
    public ItemStack getItem() {
        ItemStack item = new ItemStack(Material.FIREWORK_STAR, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("Batería de maná");
        meta.setCustomModelData(CustomItemsModelData.MANA_BATTERY.getModelData());
        item.setItemMeta(meta);
        NBTItem nbt = new NBTItem(item, true);
        nbt.setBoolean(IS_MANA_BATTERY_TAG, true);
        nbt.setInteger(MAX_MANA_CAPACITY_TAG, MAX_MANA_CAPACITY);
        nbt.setInteger(CURRENT_MANA_TAG, 0);
        updateItemLore(item);
        return item;
    }

    @Override
    public Recipe getRecipe() {
        NamespacedKey nsKey = new NamespacedKey(JavaPlugin.getPlugin(Main.class), "mana_battery");
        ShapedRecipe recipe = new ShapedRecipe(nsKey, getItem());
        RecipeChoice.ExactChoice manaEssence = new RecipeChoice.ExactChoice((new ManaEssence()).getItem());
        RecipeChoice.ExactChoice magicEssence = new RecipeChoice.ExactChoice((new MagicEssence()).getItem());
        recipe.shape("ama", "qdq", "aka");
        recipe.setIngredient('m', manaEssence);
        recipe.setIngredient('k', magicEssence);
        recipe.setIngredient('d', Material.DIAMOND);
        recipe.setIngredient('q', Material.QUARTZ);
        recipe.setIngredient('a', Material.AMETHYST_SHARD);
        return recipe;
    }

    public static boolean isBattery(@Nullable ItemStack item) {
        if (item == null) return false;
        NBTItem nbt = new NBTItem(item, true);
        return nbt.hasTag(IS_MANA_BATTERY_TAG) && nbt.getBoolean(IS_MANA_BATTERY_TAG);
    }

    public static int getMaxManaCapacity(ItemStack item) {
        NBTItem nbt = new NBTItem(item, true);
        return nbt.getInteger(MAX_MANA_CAPACITY_TAG);
    }

    public static int getCurrentMana(@Nullable ItemStack item) {
        NBTItem nbt = new NBTItem(item, true);
        return nbt.getInteger(CURRENT_MANA_TAG);
    }

    public static void setCurrentMana(ItemStack item, int currentMana) {
        NBTItem nbt = new NBTItem(item, true);
        nbt.setInteger(CURRENT_MANA_TAG, currentMana);
        updateItemLore(item);
    }

    public static void updateItemLore(ItemStack item) {
        NBTItem nbt = new NBTItem(item, true);
        int maxManaCapacity = nbt.getInteger(MAX_MANA_CAPACITY_TAG);
        int currentMana = nbt.getInteger(CURRENT_MANA_TAG);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setLore(Arrays.asList(String.format("Maná almacenado: %s / %s",
                ManaTextFormatter.formatManaAmount(currentMana),
                ManaTextFormatter.formatManaAmount(maxManaCapacity)))
        );
        item.setItemMeta(itemMeta);
    }
}
