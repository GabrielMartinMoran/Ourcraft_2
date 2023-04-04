package com.gabrielmartinmoran.ourcraft.ourcraft_2.mobs.mobs;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;

public class PossessedPiglin extends CustomMob {

    @Override
    public void spawn(World world, Location location) {
        spawnMythicMobsEntity(location, "PossessedPiglin");
    }

    @Override
    public List<Material> getNaturalDropsToRemove() {
        ArrayList<Material> drops = new ArrayList<Material>();
        drops.add(Material.ROTTEN_FLESH);
        return drops;
    }
}
