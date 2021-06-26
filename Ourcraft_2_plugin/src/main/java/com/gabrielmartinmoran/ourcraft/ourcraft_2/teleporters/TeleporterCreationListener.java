package com.gabrielmartinmoran.ourcraft.ourcraft_2.listeners;

import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

public class StructureCreationListener implements Listener {

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        if(event.getRightClicked() instanceof ItemFrame) {
            ItemFrame itemFrame = (ItemFrame) event.getRightClicked();
            this.hasPlacedLodestoneCompass(itemFrame, event.getPlayer());
        }
    }

    @EventHandler
    public void onBlockPlaced(BlockPlaceEvent event) {

    }

    private boolean hasPlacedLodestoneCompass(ItemFrame itemFrame, Player player) {
        ItemStack item = itemFrame.getItem();
        ItemStack playerItem = player.getInventory().getItemInMainHand();
        if (playerItem == null) playerItem = player.getInventory().getItemInOffHand();
        if (item.equals(Material.AIR) && playerItem != null && playerItem.getType().equals(Material.COMPASS)) {
            NBTItem nbt = new NBTItem(playerItem);
            
        }
        return false;
    }

    public boolean teleportMonumentCompleted(Location location) {
        return false;
    }
}
