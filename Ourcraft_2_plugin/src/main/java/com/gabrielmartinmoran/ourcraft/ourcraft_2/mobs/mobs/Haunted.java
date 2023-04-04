package com.gabrielmartinmoran.ourcraft.ourcraft_2.mobs.mobs;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.utils.PlayerHeads;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Mob;

public class Haunted extends CustomMob {

    private final double MAX_HEALTH = 30;
    private final double FOLLOW_RANGE = 32;
    private final double ATTACK_DAMAGE = 5;
    private final Color CHESTPLATE_COLOR = Color.fromRGB(51, 41, 13);
    private final Color LEGGINGS_COLOR = Color.fromRGB(33, 26, 5);
    private final Color BOOTS_COLOR = Color.fromRGB(18, 14, 5);
    private final String HEAD_TEXTURE = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2ZkMmRkMWQ1YzkzZTU5NWU3OWJmMWRkYTMzMmJiODJkMjNlYzk2M2U3YTMwNGZjMjFjMjM0ZGY0NWE2ZWYifX19";

    @Override
    public void spawn(World world, Location location) {
        Mob mob = this.spawnEntity(world, location, EntityType.HUSK);
        mob.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(MAX_HEALTH);
        mob.setHealth(MAX_HEALTH);
        mob.getAttribute(Attribute.GENERIC_FOLLOW_RANGE).setBaseValue(FOLLOW_RANGE);
        mob.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(ATTACK_DAMAGE);
        mob.getEquipment().setHelmet(PlayerHeads.get(HEAD_TEXTURE, "Cabeza de Haunted"));
        mob.getEquipment().setHelmetDropChance(0.1f);
        mob.getEquipment().setChestplate(this.getLeatherEquipment(Material.LEATHER_CHESTPLATE, CHESTPLATE_COLOR));
        mob.getEquipment().setLeggings(this.getLeatherEquipment(Material.LEATHER_LEGGINGS, LEGGINGS_COLOR));
        mob.getEquipment().setBoots(this.getLeatherEquipment(Material.LEATHER_BOOTS, BOOTS_COLOR));
    }
}
