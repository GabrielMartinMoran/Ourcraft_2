package com.gabrielmartinmoran.ourcraft.ourcraft_2.custommobs.mobs;

import org.bukkit.Location;
import org.bukkit.World;

public class WitherBoss extends CustomMob {

    @Override
    public void spawn(World world, Location location) {
        spawnMythicMobsEntity(location, "WitherBoss");
    }
}
