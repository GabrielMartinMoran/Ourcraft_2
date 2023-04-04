package com.gabrielmartinmoran.ourcraft.ourcraft_2.items.mana.potions;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.CustomItem;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.CustomItemsModelData;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public abstract class BaseManaPotion extends CustomItem {

    public static final String IS_MANA_POTION_TAG = "isManaPotion";
    public static final String MANA_RECOVER_AMOUNT_TAG = "manaRecoverAmount";

    @Override
    public ItemStack getItem() {
        ItemStack item = new ItemStack(Material.HONEY_BOTTLE, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(this.getName());
        meta.setCustomModelData(CustomItemsModelData.MANA_POTION.getModelData());
        meta.setLore(Arrays.asList(String.format("Poción que recupera %d puntos de mana",
                this.getManaRecoverAmount())));
        item.setItemMeta(meta);
        NBTItem nbt = new NBTItem(item);
        nbt.setBoolean(IS_MANA_POTION_TAG, true);
        nbt.setInteger(MANA_RECOVER_AMOUNT_TAG, this.getManaRecoverAmount());
        return nbt.getItem();
    }

    @Override
    public Recipe getRecipe() {
        return null;
    }

    protected String getName() {
        return "Poción de maná";
    }

    protected int getManaRecoverAmount() {
        return 0;
    }
}
