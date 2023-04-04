package com.gabrielmartinmoran.ourcraft.ourcraft_2.listeners;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.player_data.AttributeLevelingHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;

public class ItemEnchantingListener implements Listener {
    private AttributeLevelingHandler attributeLevelingHandler;

    public ItemEnchantingListener() {
        this.attributeLevelingHandler = new AttributeLevelingHandler();
    }

    @EventHandler
    public void onItemEnchant(EnchantItemEvent event) {
        this.attributeLevelingHandler.onItemEnchant(event.getEnchanter(), event.getExpLevelCost());
    }
}
