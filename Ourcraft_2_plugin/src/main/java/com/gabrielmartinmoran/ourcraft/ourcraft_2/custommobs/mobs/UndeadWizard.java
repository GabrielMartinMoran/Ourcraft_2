package com.gabrielmartinmoran.ourcraft.ourcraft_2.custommobs.mobs;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.MagicEssence;
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

public class UndeadWizard extends CustomMob {

    @Override
    public void spawn(World world, Location location) {
        spawnMythicMobsEntity(location, "UndeadWizard");
    }
    @Override
    public List<MobDrop> getCustomDrops() {
        ArrayList<MobDrop> drops = new ArrayList<MobDrop>();
        drops.add(new MobDrop(3, 2, new MagicEssence().getItem(), 1, 4));
        drops.add(new MobDrop(1, 2, new SpellScroll(SpellTypes.POISON_CLOUD, 1).getItem(), 1, 1));
        drops.add(new MobDrop(1, 2, new SpellScroll(SpellTypes.MAGIC_MISSILES, 1).getItem(), 1, 1));
        drops.add(new MobDrop(1, 2, new SpellScroll(SpellTypes.BURNING_ORB, 1).getItem(), 1, 1));
        drops.add(new MobDrop(3, 1, new ManaEssence().getItem(), 1, 5));
        drops.add(new MobDrop(1, 0, null, 1, 1));
        return drops;
    }
}
