package com.gabrielmartinmoran.ourcraft.ourcraft_2.mobs.mobs;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.MagicEssence;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.ManaEssence;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.OrcSkin;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.spells.SpellScroll;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.mobs.MobDrop;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.spells.SpellTypes;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;

public class OrcShaman extends CustomMob {

    @Override
    public void spawn(World world, Location location) {
        spawnMythicMobsEntity(location, "OrcShaman");
    }

    @Override
    public List<Material> getNaturalDropsToRemove() {
        ArrayList<Material> drops = new ArrayList<Material>();
        drops.add(Material.ROTTEN_FLESH);
        return drops;
    }

    @Override
    public List<MobDrop> getCustomDrops() {
        ArrayList<MobDrop> drops = new ArrayList<MobDrop>();
        drops.add(new MobDrop(2, 3, new MagicEssence().getItem(), 1, 1));
        drops.add(new MobDrop(2, 2, new ManaEssence().getItem(), 1, 2));
        drops.add(new MobDrop(1, 2, new SpellScroll(SpellTypes.POISON_CLOUD, 1).getItem(), 1, 1));
        drops.add(new MobDrop(3, 1, new OrcSkin().getItem(), 1, 1));
        drops.add(new MobDrop(2, 0, null, 1, 1));
        return drops;
    }
}
