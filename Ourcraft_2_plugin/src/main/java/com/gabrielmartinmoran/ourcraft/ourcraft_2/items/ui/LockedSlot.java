package com.gabrielmartinmoran.ourcraft.ourcraft_2.items.ui;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.CustomItemsModelData;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class LockedSlot {


    public static ItemStack generate() {
        ItemStack barrier = new ItemStack(Material.BARRIER);
        ItemMeta barrierMeta = barrier.getItemMeta();
        barrierMeta.setDisplayName(" ");
        barrierMeta.setCustomModelData(CustomItemsModelData.LOCKED_INVENTORY_SLOT.getModelData());
        barrier.setItemMeta(barrierMeta);
        return barrier;
    }
}
