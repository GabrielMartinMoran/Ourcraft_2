package com.gabrielmartinmoran.ourcraft.ourcraft_2.spells;

import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;

import java.lang.management.BufferPoolMXBean;

public class SpellsTrailsService implements Runnable{

    public static int EXECUTE_EACH_TICKS = 1;

    @Override
    public void run() {
        for (Entity entity: SpellsResolver.flyingSpellEntities.keySet()) {
            SpellTrailInfo trailInfo = SpellsResolver.flyingSpellEntities.get(entity);
            if (trailInfo == null) continue;
            if (trailInfo.getParticle().equals(Particle.REDSTONE)) {
                Particle.DustOptions dustOptions = new Particle.DustOptions(trailInfo.getColor(), 1);
                entity.getWorld().spawnParticle(Particle.REDSTONE, entity.getLocation(), 50, dustOptions);
            } else {
                entity.getWorld().spawnParticle(trailInfo.getParticle(), entity.getLocation(), trailInfo.getParticleAmmunt(), 0.1d, 0.1d, 0, 0.1F);
            }
        }
    }
}
