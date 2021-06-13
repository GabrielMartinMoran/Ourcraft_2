package com.gabrielmartinmoran.ourcraft.ourcraft_2.custommobs.mobs;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Phantom;
import org.bukkit.entity.Skeleton;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PhantomRider extends CustomMob {

    private final int PHANTOM_SIZE = 2;
    private final int PHANTOM_MAX_HEALTH = 25;
    private final int FOLLOW_RANGE = 64;


    @Override
    public void spawn(World world, Location location) {
        Phantom phantom = (Phantom) this.spawnEntity(world, location, EntityType.PHANTOM);
        phantom.setSize(PHANTOM_SIZE);
        phantom.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(PHANTOM_MAX_HEALTH);
        phantom.setHealth(PHANTOM_MAX_HEALTH);
        phantom.getAttribute(Attribute.GENERIC_FOLLOW_RANGE).setBaseValue(FOLLOW_RANGE);

        Skeleton passenger = (Skeleton) this.spawnEntity(world, location, EntityType.SKELETON);
        passenger.getAttribute(Attribute.GENERIC_FOLLOW_RANGE).setBaseValue(FOLLOW_RANGE);
        passenger.getEquipment().setItemInMainHand(new ItemStack(Material.WOODEN_SWORD));
        passenger.getEquipment().setItemInOffHand(this.getBow());
        passenger.getEquipment().setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE));
        passenger.getEquipment().setHelmet(new ItemStack(Material.CHAINMAIL_HELMET));
        phantom.addPassenger(passenger);
    }

    private ItemStack getBow() {
        ItemStack itemStack = new ItemStack(Material.BOW);
        itemStack.addEnchantment(Enchantment.ARROW_KNOCKBACK, 1);
        itemStack.addEnchantment(Enchantment.ARROW_DAMAGE, 1);
        return itemStack;
    }
}
