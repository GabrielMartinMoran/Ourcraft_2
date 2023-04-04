package com.gabrielmartinmoran.ourcraft.ourcraft_2.mobs.mobs;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.ManaEssence;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.mobs.MobDrop;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class IceSoul extends CustomMob {

    @Override
    public void spawn(World world, Location location) {
        spawnMythicMobsEntity(location, "IceSoul");
    }

    @Override
    public boolean preventNaturalDrops() {
        return true;
    }

    @Override
    public List<MobDrop> getCustomDrops() {
        ArrayList<MobDrop> drops = new ArrayList<MobDrop>();
        drops.add(new MobDrop(3, 1, new ManaEssence().getItem(), 1, 4));
        drops.add(new MobDrop(4, 1, new ItemStack(Material.BLUE_ICE), 1, 2));
        drops.add(new MobDrop(3, 0, null, 1, 1));
        return drops;
    }
}
