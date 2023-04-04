package com.gabrielmartinmoran.ourcraft.ourcraft_2.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryInteractEvent;

public class ItemStackModifierListener implements Listener {
    /**
     * This listener will update the player's inventory the tick after an inventory click. This is required
     * since the client predicts how the inventory will look afterwards. When the server has modified stack
     * sizes the prediction might be wrong.
     */
    /*
    @EventHandler(priority = EventPriority.MONITOR)
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.isCancelled()) {
            return;
        }
        if (event.getWhoClicked() instanceof Player) {
            Player player = (Player) event.getWhoClicked();
            player.updateInventory();

            // The creative inventory works differently to the survival inventory and will not work
            // properly when updating the inventory the tick after a click.
            if (player.getGameMode() == GameMode.SURVIVAL || player.getGameMode() == GameMode.ADVENTURE) {
                Bukkit.getScheduler().runTask(JavaPlugin.getPlugin(Main.class), player::updateInventory);
            } else {
                Bukkit.getScheduler().runTask(JavaPlugin.getPlugin(Main.class), player::updateInventory);
            }

        }
    }*/
}
