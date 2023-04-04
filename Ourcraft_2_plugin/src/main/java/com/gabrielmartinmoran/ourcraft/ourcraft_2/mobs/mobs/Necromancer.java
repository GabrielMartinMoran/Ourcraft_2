package com.gabrielmartinmoran.ourcraft.ourcraft_2.mobs.mobs;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.MagicEssence;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.ManaEssence;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.spells.SpellScroll;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.mobs.MobDrop;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.spells.SpellTypes;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;

public class Necromancer extends CustomMob {

    @Override
    public void spawn(World world, Location location) {
        spawnMythicMobsEntity(location, "Necromancer");
    }

    @Override
    public boolean preventNaturalDrops() {
        return true;
    }

    @Override
    public List<MobDrop> getCustomDrops() {
        ArrayList<MobDrop> drops = new ArrayList<MobDrop>();
        drops.add(new MobDrop(2, 2, new MagicEssence().getItem(), 1, 3));
        drops.add(new MobDrop(1, 2, new SpellScroll(SpellTypes.NECROMANCER, 1).getItem(), 1, 1));
        drops.add(new MobDrop(3, 1, new ManaEssence().getItem(), 1, 4));
        drops.add(new MobDrop(4, 0, null, 1, 1));
        return drops;
    }
}
