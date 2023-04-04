package com.gabrielmartinmoran.ourcraft.ourcraft_2.utils;

import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.inventory.ItemStack;

public class BagUtils {

    public static boolean isBag(ItemStack item) {
        if (item == null) return false;
        NBTItem nbt = new NBTItem(item);
        return nbt.hasKey("isBag") && nbt.getBoolean("isBag");
    }
}
