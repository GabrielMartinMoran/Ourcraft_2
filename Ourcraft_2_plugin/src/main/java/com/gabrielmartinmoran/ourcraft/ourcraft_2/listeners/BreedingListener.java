package com.gabrielmartinmoran.ourcraft.ourcraft_2.listeners;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.player_data.AttributeLevelingHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityBreedEvent;

public class BreedingListener implements Listener {

    private AttributeLevelingHandler attributeLevelingHandler;

    public BreedingListener() {
        this.attributeLevelingHandler = new AttributeLevelingHandler();
    }

    @EventHandler
    public void onBreeding(EntityBreedEvent event) {
        if(event.getBreeder() instanceof Player) {
            this.attributeLevelingHandler.onBreeding((Player) event.getBreeder());
        }
    }
}
