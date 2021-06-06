package com.gabrielmartinmoran.ourcraft.ourcraft_2.custommobs.mobs;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Phantom;

public class BigPhantom extends CustomMob {

    private final int SIZE = 10;
    private final int MAX_HEALTH = 30;
    private final int FOLLOW_RANGE = 64;

    @Override
    public void spawn(World world, Location location) {
        Phantom mob = (Phantom) this.spawnEntity(world, location, EntityType.PHANTOM);
        mob.setSize(SIZE);
        mob.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(MAX_HEALTH);
        mob.setHealth(MAX_HEALTH);
        mob.getAttribute(Attribute.GENERIC_FOLLOW_RANGE).setBaseValue(FOLLOW_RANGE);
    }
}
