package com.gabrielmartinmoran.ourcraft.ourcraft_2.inventories;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.utils.BagUtils;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class NBTInventoryInteractionListener implements Listener {

    private NBTInventoriesCache cache;


    public NBTInventoryInteractionListener(NBTInventoriesCache cache) {
        this.cache = cache;
    }

    @EventHandler
    public void onInventoryItemClick(InventoryClickEvent event) {
        Inventory inventory = event.getInventory();
        ItemStack item = event.getCurrentItem();
        if (this.cache.contains(inventory)) {
            NBTInventory nbtInventory = this.cache.get(inventory);
            // BagListener handles bag events
            if (BagUtils.isBag(nbtInventory.getItem())) return;
            // Prevent barriers
            if (item != null && item.getType().equals(Material.BARRIER)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        Inventory inventory = event.getInventory();
        if (this.cache.contains(inventory)) {
            NBTInventory nbtInventory = this.cache.get(inventory);
            // BagListener handles bag events
            if (BagUtils.isBag(nbtInventory.item)) return;
            this.cache.remove(inventory);
        }
    }


}
