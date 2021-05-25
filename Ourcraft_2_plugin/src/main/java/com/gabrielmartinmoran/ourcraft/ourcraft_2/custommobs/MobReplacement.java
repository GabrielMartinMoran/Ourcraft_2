package com.gabrielmartinmoran.ourcraft.ourcraft_2.custommobs;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.custommobs.mobs.CustomMob;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.custommobs.mobs.Haunted;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.HashMap;

public class MobReplacement {

    private double spawnProbability;
    private CustomMobsTypes mobType;
    private HashMap<CustomMobsTypes, CustomMob> cutomMobs;

    public MobReplacement(double spawnProbability, CustomMobsTypes mobType) {
        this.spawnProbability = spawnProbability;
        this.mobType = mobType;
        this.cutomMobs = new HashMap<CustomMobsTypes, CustomMob>();
        this.cutomMobs.put(CustomMobsTypes.HAUNTED, new Haunted());
    }

    public void spawn(World world, Location location) {
        cutomMobs.get(mobType).spawn(world, location);
    }

    public double getSpawnProbability() {
        return spawnProbability;
    }

    public CustomMobsTypes getMobType() {
        return mobType;
    }
}
