package com.gabrielmartinmoran.ourcraft.ourcraft_2.listeners;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.inventories.NBTInventoriesCache;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.inventories.NBTInventory;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.utils.BagUtils;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.utils.ItemUtils;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class BagListener implements Listener {
    private ItemUtils itemUtils;
    private NBTInventoriesCache cache;

    public BagListener(NBTInventoriesCache cache) {
        this.itemUtils = new ItemUtils();
        this.cache = cache;
    }

    @EventHandler
    public void onItemClick(PlayerInteractEvent event) {
        ItemStack item = event.getItem();
        Action action = event.getAction();
        if (BagUtils.isBag(item) && (action.equals(Action.RIGHT_CLICK_AIR) || (action.equals(Action.RIGHT_CLICK_BLOCK)))) {
            this.showBagInventory(event.getPlayer(), item);
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        Inventory inventory = event.getInventory();
        if (this.cache.contains(inventory)) {
            NBTInventory nbtInventory = this.cache.get(inventory);
            // Only bags
            if (!BagUtils.isBag(nbtInventory.getItem())) return;
            nbtInventory.save();
            this.damageBag((Player) event.getPlayer(), nbtInventory.getItem(), inventory);
            this.cache.remove(inventory);
        }
    }

    @EventHandler
    public void onInventoryItemClick(InventoryClickEvent event) {
        Inventory inventory = event.getInventory();
        ItemStack item = event.getCurrentItem();
        if (this.cache.contains(inventory)) {
            NBTInventory nbtInventory = this.cache.get(inventory);
            // Only bags
            if (!BagUtils.isBag(nbtInventory.getItem())) return;
            if (item != null && (item.getType().equals(Material.BARRIER) || item.getType().toString().contains("SHULKER_BOX"))) {
                event.setCancelled(true);
            }
        }
    }

    private void showBagInventory(Player player, ItemStack item) {
        NBTInventory nbtInventory = new NBTInventory(item);
        this.cache.put(nbtInventory.getInventory(), nbtInventory);
        player.openInventory(nbtInventory.getInventory());
    }

    private void damageBag(Player player, ItemStack bag, Inventory inventory) {
        if (player.getGameMode().equals(GameMode.CREATIVE)) return;
        boolean destroyed = this.itemUtils.reduceItemDurability(player, bag);
        if (destroyed) {
            for (ItemStack item : inventory.getContents()) {
                if (item.getType() != Material.BARRIER) player.getWorld().dropItem(player.getLocation(), item);
            }
        }
    }
}
