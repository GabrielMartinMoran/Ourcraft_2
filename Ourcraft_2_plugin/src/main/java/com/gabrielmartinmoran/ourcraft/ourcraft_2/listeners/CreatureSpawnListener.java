package com.gabrielmartinmoran.ourcraft.ourcraft_2.listeners;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.mobs.CustomMobsManager;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class CreatureSpawnListener implements Listener {

    private CustomMobsManager customMobsSpawner;

    public CreatureSpawnListener() {
        this.customMobsSpawner = new CustomMobsManager();
    }

    @EventHandler
    public void onMobSpawn(CreatureSpawnEvent event) {
        if (event.getEntity() instanceof LivingEntity && !event.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.CUSTOM)) {
            LivingEntity mob = (LivingEntity) event.getEntity();
            if (!this.customMobsSpawner.isCustomMob(mob)) this.customMobsSpawner.replaceIfNeeded(mob);
        }
    }
}
