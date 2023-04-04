package com.gabrielmartinmoran.ourcraft.ourcraft_2.mobs.mobs;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.utils.PlayerHeads;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;

public class HoglinTamer extends CustomMob {

    private final int FOLLOW_RANGE = 64;
    private final String HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2UyZmJhZWYzMzFlOTA1ZDRiMmM1ZTY5MzVkZWY5NGM4ZTRjMzJlY2EwMzE3ZDE0M2E0NDExOTNhZThmODI0ZSJ9fX0";


    @Override
    public void spawn(World world, Location location) {
        Hoglin hoglin = (Hoglin) this.spawnEntity(world, location, EntityType.HOGLIN);
        hoglin.setImmuneToZombification(true);
        hoglin.getAttribute(Attribute.GENERIC_FOLLOW_RANGE).setBaseValue(FOLLOW_RANGE);
        hoglin.setAdult();

        Zombie passenger = (Zombie) this.spawnEntity(world, location, EntityType.ZOMBIE);
        passenger.setBaby();
        passenger.getAttribute(Attribute.GENERIC_FOLLOW_RANGE).setBaseValue(FOLLOW_RANGE);
        passenger.getEquipment().setItemInMainHand(this.getSword());
        passenger.getEquipment().setHelmet(PlayerHeads.get(HEAD, "Cabeza de domador de Hoglin"));
        passenger.getEquipment().setHelmetDropChance(0.1f);
        passenger.getEquipment().setChestplate(new ItemStack(Material.GOLDEN_CHESTPLATE));
        passenger.getEquipment().setLeggings(new ItemStack(Material.GOLDEN_LEGGINGS));
        passenger.getEquipment().setBoots(new ItemStack(Material.GOLDEN_BOOTS));
        hoglin.addPassenger(passenger);
    }

    private ItemStack getSword() {
        ItemStack itemStack = new ItemStack(Material.GOLDEN_SWORD);
        itemStack.addEnchantment(Enchantment.DAMAGE_ALL, 2);
        itemStack.addEnchantment(Enchantment.FIRE_ASPECT, 2);
        return itemStack;
    }
}
