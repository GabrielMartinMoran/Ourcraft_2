package com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.potions;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.CustomItem;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;

public abstract class BaseManaPotion implements CustomItem {

    public static final String IS_MANA_POTION_TAG = "isManaPotion";
    public static final String MANA_RECOVER_AMOUNT_TAG = "manaRecoverAmount";

    @Override
    public ItemStack getItem() {
        ItemStack item = new ItemStack(Material.POTION, 1);
        PotionMeta meta = (PotionMeta) item.getItemMeta();
        meta.setDisplayName(this.getName());
        meta.setColor(Color.fromRGB(3, 152, 252));
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
        return "Poción de mana";
    }

    protected int getPotionLevel() {
        return 1;
    }

    protected int getManaRecoverAmount() {
        return 0;
    }
}
