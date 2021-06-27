package com.gabrielmartinmoran.ourcraft.ourcraft_2.custommobs.mobs;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.MagicEssence;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.ManaEssence;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.custommobs.CustomMobsTypes;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.custommobs.MobDrop;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class WaterDriad extends CustomMob {

    @Override
    public void spawn(World world, Location location) {
        spawnMythicMobsEntity(location, "WaterDriad");
    }

    @Override
    public boolean preventNaturalDrops() {
        return true;
    }

    @Override
    public List<MobDrop> getCustomDrops() {
        ArrayList<MobDrop> drops = new ArrayList<MobDrop>();
        drops.add(new MobDrop(1, 2, new MagicEssence().getItem(), 1, 3));
        drops.add(new MobDrop(2, 1, new ManaEssence().getItem(), 1, 4));
        drops.add(new MobDrop(7, 0, null, 1, 1));
        return drops;
    }
}
