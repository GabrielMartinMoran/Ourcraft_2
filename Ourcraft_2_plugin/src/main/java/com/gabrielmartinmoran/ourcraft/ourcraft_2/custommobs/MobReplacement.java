package com.gabrielmartinmoran.ourcraft.ourcraft_2.custommobs;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.custommobs.mobs.CustomMob;
import org.bukkit.Location;
import org.bukkit.World;

public class MobReplacement {

    private double spawnWeight;
    private CustomMobsTypes mobType;

    public MobReplacement(double spawnWeight, CustomMobsTypes mobType) {
        this.spawnWeight = spawnWeight;
        this.mobType = mobType;
    }

    public void spawn(World world, Location location) {
        if(this.mobType.getCustomMob() == null) {
            System.out.println("No se ha especificado una instancia de CustomMob para el mob " + this.mobType.name());
            return;
        }
        CustomMob customMob = this.mobType.getCustomMob();
        customMob.spawn(world, location);
    }

    public double getSpawnWeight() {
        return spawnWeight;
    }

    public CustomMobsTypes getMobType() {
        return mobType;
    }
}
