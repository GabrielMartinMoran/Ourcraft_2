package com.gabrielmartinmoran.ourcraft.ourcraft_2.custommobs.mobs;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.custommobs.CustomMobsTypes;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.custommobs.MobDrop;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Mimic extends CustomMob {

    @Override
    public void spawn(World world, Location location) {
        spawnMythicMobsEntity(location, "Mimic");
    }

    @Override
    public boolean preventNaturalDrops() {
        return true;
    }

    @Override
    public List<MobDrop> getCustomDrops() {
        ArrayList<MobDrop> drops = new ArrayList<MobDrop>();
        drops.add(new MobDrop(25, 1, new ItemStack(Material.IRON_INGOT), 1, 3));
        drops.add(new MobDrop(25, 2, new ItemStack(Material.GOLD_INGOT), 1, 3));
        drops.add(new MobDrop(5, 3, new ItemStack(Material.DIAMOND), 1, 1));
        drops.add(new MobDrop(2, 4, new ItemStack(Material.EMERALD), 1, 1));
        drops.add(new MobDrop(1, 5, new ItemStack(Material.BARREL), 1, 1));
        drops.add(new MobDrop(42, 0, null, 1, 1));
        return drops;
    }
}
