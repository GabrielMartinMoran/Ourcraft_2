package com.gabrielmartinmoran.ourcraft.ourcraft_2.listeners;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.blocks.states.CustomBlockStateProvider;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.blocks.states.block_states.CustomBlockState;
import org.bukkit.GameMode;
import org.bukkit.block.Block;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class CustomBlockListener implements Listener {

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (CustomBlockStateProvider.needsState(event.getItemInHand())) {
            CustomBlockStateProvider.putFromItem(event.getItemInHand(), event.getBlock(), event.getPlayer());
            CustomBlockState state = CustomBlockStateProvider.get(event.getBlock());
            state.onPlace();
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockClick(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK && !event.getPlayer().isSneaking()) {
            if (CustomBlockStateProvider.contains(event.getClickedBlock())) {
                CustomBlockState state = CustomBlockStateProvider.get(event.getClickedBlock());
                state.onInteract(event.getPlayer());
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onInventoryItemClick(InventoryClickEvent event) {
        CustomBlockState state = CustomBlockStateProvider.searchByInventory(event.getInventory());
        if (state != null) state.onInventoryItemClick(event);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (!CustomBlockStateProvider.contains(event.getBlock())) return;
        CustomBlockState state = CustomBlockStateProvider.get(event.getBlock());
        state.onDestroy(event.getPlayer());
        // Close the inventory for the viewers
        if (state.getInventory() != null) {
            for (HumanEntity entity : state.getInventory().getViewers()) {
                entity.closeInventory();
            }
        }
        CustomBlockStateProvider.remove(event.getBlock());

        // If it dropped an item
        if (event.isDropItems() && event.getPlayer().getGameMode() != GameMode.CREATIVE) {
            event.setDropItems(false);
            event.getBlock().getWorld().dropItem(event.getBlock().getLocation(), state.getBlockItem());
        }

    }

    @EventHandler
    public void onInventoryMoveItem(InventoryMoveItemEvent event) {
        Block block = event.getDestination().getLocation().getBlock();
        if (CustomBlockStateProvider.contains(block)) {
            event.setCancelled(true);
        }

    }

    @EventHandler
    public void onPistonExtendEvent(BlockPistonExtendEvent e) {
        for (Block block : e.getBlocks()) {
            if (CustomBlockStateProvider.contains(block)) {
                e.setCancelled(true);
                return;
            }
        }
    }

    @EventHandler
    public void onPistonRetractEvent(BlockPistonRetractEvent e) {
        for (Block block : e.getBlocks()) {
            if (CustomBlockStateProvider.contains(block)) {
                e.setCancelled(true);
                return;
            }
        }
    }
}
