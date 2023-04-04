package com.gabrielmartinmoran.ourcraft.ourcraft_2.mobs.mobs;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.OrcSkin;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.mobs.MobDrop;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;

public class OrcBrute extends CustomMob {

    @Override
    public void spawn(World world, Location location) {
        spawnMythicMobsEntity(location, "OrcBrute");
    }

    @Override
    public List<Material> getNaturalDropsToRemove() {
        ArrayList<Material> drops = new ArrayList<Material>();
        drops.add(Material.ROTTEN_FLESH);
        return drops;
    }

    @Override
    public List<MobDrop> getCustomDrops() {
        ArrayList<MobDrop> drops = new ArrayList<MobDrop>();
        drops.add(new MobDrop(3, 1, new OrcSkin().getItem(), 1, 1));
        drops.add(new MobDrop(7, 0, null, 1, 1));
        return drops;
    }
}
