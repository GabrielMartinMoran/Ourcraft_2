package com.gabrielmartinmoran.ourcraft.ourcraft_2.inventories;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.ui.LockedSlot;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryBuilder {

    private static final int SIZE_PER_ROW = 9;

    public static Inventory build(int size, String name) {
        // Complete with slots until the size is a multiple of SIZE_PER_ROW
        int totalSize = size;
        if (totalSize % SIZE_PER_ROW != 0)
            totalSize += SIZE_PER_ROW - (totalSize % SIZE_PER_ROW);

        Inventory inventory = Bukkit.createInventory(null, totalSize, name);
        ItemStack lockedSlot = LockedSlot.generate();
        int lockedSpaces = totalSize - size;
        int leftLockedSpaces = lockedSpaces / 2;
        int rightLockedSpaces = lockedSpaces / 2;
        // If it's odd
        if (lockedSpaces % 2 != 0) rightLockedSpaces++;
        for (int i = 0; i < leftLockedSpaces; i++) {
            inventory.setItem(i, lockedSlot);
        }
        for (int i = 0; i < rightLockedSpaces; i++) {
            inventory.setItem(leftLockedSpaces + size + i, lockedSlot);
        }
        return inventory;
    }
}
