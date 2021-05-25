package com.gabrielmartinmoran.ourcraft.ourcraft_2.utils;


import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.inventory.ItemStack;

public class ItemUtils {

    public boolean hasTag(ItemStack item, String tag) {
        NBTItem nbti = new NBTItem(item);
        return nbti.getInteger(tag) == 1;
    }
}
