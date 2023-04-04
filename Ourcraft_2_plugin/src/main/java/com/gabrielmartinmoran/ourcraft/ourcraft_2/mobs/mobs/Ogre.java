package com.gabrielmartinmoran.ourcraft.ourcraft_2.mobs.mobs;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.OgreSkin;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.mobs.MobDrop;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;

public class Ogre extends CustomMob {

    @Override
    public void spawn(World world, Location location) {
        spawnMythicMobsEntity(location, "Ogre");
    }

    @Override
    public boolean preventNaturalDrops() {
        return true;
    }

    @Override
    public List<MobDrop> getCustomDrops() {
        ArrayList<MobDrop> drops = new ArrayList<MobDrop>();
        drops.add(new MobDrop(7, 1, new OgreSkin().getItem(), 1, 1));
        drops.add(new MobDrop(3, 0, null, 1, 1));
        return drops;
    }
}
