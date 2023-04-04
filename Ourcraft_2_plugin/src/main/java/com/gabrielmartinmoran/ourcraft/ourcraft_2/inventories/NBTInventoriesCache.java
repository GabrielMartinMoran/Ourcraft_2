package com.gabrielmartinmoran.ourcraft.ourcraft_2.inventories;

import org.bukkit.inventory.Inventory;

import java.util.HashMap;

public class NBTInventoriesCache {

    private HashMap<Inventory, NBTInventory> nbtInventories;

    public NBTInventoriesCache() {
        this.nbtInventories = new HashMap<Inventory, NBTInventory>();
    }

    public void put(Inventory inventory, NBTInventory nbtInventory) {
        this.nbtInventories.put(inventory, nbtInventory);
    }

    public void remove(Inventory inventory) {
        this.nbtInventories.remove(inventory);
    }

    public NBTInventory get(Inventory inventory) {
        return this.nbtInventories.get(inventory);
    }

    public boolean contains(Inventory inventory) {
        return this.nbtInventories.containsKey(inventory);
    }
}
