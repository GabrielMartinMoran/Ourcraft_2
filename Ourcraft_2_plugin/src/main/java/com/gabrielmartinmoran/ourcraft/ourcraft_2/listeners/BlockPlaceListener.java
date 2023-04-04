package com.gabrielmartinmoran.ourcraft.ourcraft_2.listeners;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.player_data.AttributeLevelingHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlaceListener implements Listener {

    private AttributeLevelingHandler attributeLevelingHandler;

    public BlockPlaceListener() {
        this.attributeLevelingHandler = new AttributeLevelingHandler();
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        this.attributeLevelingHandler.onBlockPlaced(event.getBlock(), event.getPlayer());
    }
}
