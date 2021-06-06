package com.gabrielmartinmoran.ourcraft.ourcraft_2.custommobs.mobs;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.utils.PlayerHeads;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Phantom;

public class KingPhantom extends CustomMob {

    private final int SIZE = 64;
    private final int MAX_HEALTH = 50;
    private final int FOLLOW_RANGE = 128;
    private final int MOVEMENT_SPEED = 50;

    @Override
    public void spawn(World world, Location location) {
        Phantom mob = (Phantom) this.spawnEntity(world, location, EntityType.PHANTOM);
        mob.setSize(SIZE);
        mob.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(MAX_HEALTH);
        mob.setHealth(MAX_HEALTH);
        mob.getAttribute(Attribute.GENERIC_FOLLOW_RANGE).setBaseValue(FOLLOW_RANGE);
        mob.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(MOVEMENT_SPEED);
    }
}
