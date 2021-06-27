package com.gabrielmartinmoran.ourcraft.ourcraft_2.custommobs.mobs;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.custommobs.CustomMobsTypes;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.custommobs.MobDrop;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.utils.PlayerHeads;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Monster;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Revenant extends CustomMob {

    @Override
    public void spawn(World world, Location location) {
        spawnMythicMobsEntity(location, "Revenant");
    }
}
