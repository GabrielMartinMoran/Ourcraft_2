package com.gabrielmartinmoran.ourcraft.ourcraft_2;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.playerdata.PlayerDataProvider;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldSaveEvent;

public class WorldSaveListener implements Listener {

    @EventHandler
    public void onWorldSave(WorldSaveEvent event) {
        PlayerDataProvider.saveAll();
    }
}
