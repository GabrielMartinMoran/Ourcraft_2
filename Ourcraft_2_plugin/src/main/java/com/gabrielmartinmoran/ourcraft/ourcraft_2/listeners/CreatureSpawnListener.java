package com.gabrielmartinmoran.ourcraft.ourcraft_2.listeners;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.custommobs.CustomMobsSpawner;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class MobSpawnListener implements Listener {

    private CustomMobsSpawner customMobsSpawner;

    public MobSpawnListener() {
        this.customMobsSpawner = new CustomMobsSpawner();
    }

    @EventHandler
    public void onMobSpawn(CreatureSpawnEvent event) {
        Creature creature = (Creature)event.getEntity();
        this.customMobsSpawner.replaceIfNeeded(creature);
    }
}
