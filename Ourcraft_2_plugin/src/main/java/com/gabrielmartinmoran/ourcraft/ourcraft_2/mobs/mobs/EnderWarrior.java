package com.gabrielmartinmoran.ourcraft.ourcraft_2.mobs.mobs;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.CombatPearl;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.MagicEssence;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.mobs.MobDrop;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class EnderWarrior extends CustomMob {

    @Override
    public void spawn(World world, Location location) {
        spawnMythicMobsEntity(location, "EnderWarrior");
    }

    @Override
    public boolean preventNaturalDrops() {
        return true;
    }

    @Override
    public List<MobDrop> getCustomDrops() {
        ArrayList<MobDrop> drops = new ArrayList<MobDrop>();
        drops.add(new MobDrop(3, 3, new CombatPearl().getItem(), 1, 1));
        drops.add(new MobDrop(3, 2, new MagicEssence().getItem(), 1, 2));
        drops.add(new MobDrop(3, 1, new ItemStack(Material.ENDER_PEARL), 1, 1));
        drops.add(new MobDrop(1, 0, null, 1, 1));
        return drops;
    }
}
