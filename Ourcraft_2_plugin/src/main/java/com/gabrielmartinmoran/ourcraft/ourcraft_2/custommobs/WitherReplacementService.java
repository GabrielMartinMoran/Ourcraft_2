package com.gabrielmartinmoran.ourcraft.ourcraft_2.custommobs;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.custommobs.mobs.WitherBoss;
import de.tr7zw.nbtapi.NBTEntity;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Wither;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class WitherReplacementService implements Runnable {

    public static int EXECUTE_EACH_TICKS = 20;
    private CustomMobsManager customMobsManager;
    private WitherBoss customWitherBoss;

    public WitherReplacementService() {
        this.customMobsManager = new CustomMobsManager();
        this.customWitherBoss = new WitherBoss();
        this.customWitherBoss.setType(CustomMobsTypes.WITHER_BOSS);
    }

    @Override
    public void run() {
        for (World world: Bukkit.getWorlds()) {
            Collection<LivingEntity> withers = world.getLivingEntities().stream().filter(x -> x.getType().equals(EntityType.WITHER)).collect(Collectors.toList());
            for(LivingEntity entity: withers) {
                Wither wither = (Wither) entity;
                if (this.customMobsManager.isCustomMob(wither)) continue;
                NBTEntity nbt = new NBTEntity(wither);
                if(nbt.getInteger("Invulnerable") == 0 && nbt.getInteger("Invul") == 0) {
                    this.customWitherBoss.spawn(world, wither.getLocation());
                    wither.remove();
                }
            }
        }
    }
}
