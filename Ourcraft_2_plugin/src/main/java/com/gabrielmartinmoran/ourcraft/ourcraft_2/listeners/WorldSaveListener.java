package com.gabrielmartinmoran.ourcraft.ourcraft_2.listeners;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.blocks.states.CustomBlockStateProvider;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.player_data.PlayerDataProvider;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.teleporters.TeleporterDataProvider;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldSaveEvent;

public class WorldSaveListener implements Listener {

    @EventHandler
    public void onWorldSave(WorldSaveEvent event) {
        PlayerDataProvider.saveAll();
        TeleporterDataProvider.saveAll();
        CustomBlockStateProvider.saveAll();
    }
}
