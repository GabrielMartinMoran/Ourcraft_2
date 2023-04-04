package com.gabrielmartinmoran.ourcraft.ourcraft_2.mobs.mobs;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.MagicEssence;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.ManaEssence;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.mobs.MobDrop;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;

public class DrownedWizard extends CustomMob {

    @Override
    public void spawn(World world, Location location) {
        spawnMythicMobsEntity(location, "DrownedWizard");
    }

    @Override
    public List<MobDrop> getCustomDrops() {
        ArrayList<MobDrop> drops = new ArrayList<MobDrop>();
        drops.add(new MobDrop(1, 2, new MagicEssence().getItem(), 1, 2));
        drops.add(new MobDrop(3, 1, new ManaEssence().getItem(), 2, 4));
        drops.add(new MobDrop(6, 0, null, 1, 1));
        return drops;
    }
}
