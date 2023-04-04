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

public class EnergyNode extends CustomMob {

    @Override
    public void spawn(World world, Location location) {
        spawnMythicMobsEntity(location, "EnergyNode");
    }

    @Override
    public boolean preventNaturalDrops() {
        return true;
    }

    @Override
    public List<MobDrop> getCustomDrops() {
        ArrayList<MobDrop> drops = new ArrayList<MobDrop>();
        drops.add(new MobDrop(2, 2, new MagicEssence().getItem(), 1, 1));
        drops.add(new MobDrop(1, 2, new SpellScroll(SpellTypes.MAGIC_SHOT, 1).getItem(), 1, 1));
        drops.add(new MobDrop(2, 1, new ManaEssence().getItem(), 1, 2));
        drops.add(new MobDrop(5, 0, null, 1, 1));
        return drops;
    }
}
