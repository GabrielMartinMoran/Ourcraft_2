package com.gabrielmartinmoran.ourcraft.ourcraft_2.custommobs.mobs;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.ManaEssence;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.spells.SpellScroll;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.custommobs.CustomMobsTypes;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.custommobs.MobDrop;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.spells.SpellTypes;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class FireElemental extends CustomMob {

    @Override
    public void spawn(World world, Location location) {
        spawnMythicMobsEntity(location, "FireElemental");
    }

    @Override
    public boolean preventNaturalDrops() {
        return true;
    }

    @Override
    public List<MobDrop> getCustomDrops() {
        ArrayList<MobDrop> drops = new ArrayList<MobDrop>();
        drops.add(new MobDrop(1, 2, new SpellScroll(SpellTypes.FIREBALL, 1).getItem(), 1, 1));
        drops.add(new MobDrop(2, 2, new ManaEssence().getItem(), 1, 4));
        drops.add(new MobDrop(3, 1, new ItemStack(Material.BLAZE_POWDER), 1, 3));
        drops.add(new MobDrop(3, 1, new ItemStack(Material.MAGMA_BLOCK), 1, 2));
        drops.add(new MobDrop(1, 0, null, 1, 1));
        return drops;
    }
}
