package com.gabrielmartinmoran.ourcraft.ourcraft_2.mobs.mobs;

import org.bukkit.Location;
import org.bukkit.World;

public class AmplifiedCreeper extends CustomMob {

    @Override
    public void spawn(World world, Location location) {
        spawnMythicMobsEntity(location, "AmplifiedCreeper");
    }
}
