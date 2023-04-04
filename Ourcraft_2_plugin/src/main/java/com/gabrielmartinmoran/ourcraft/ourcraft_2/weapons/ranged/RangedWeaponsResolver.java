package com.gabrielmartinmoran.ourcraft.ourcraft_2.weapons.ranged;

import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class RangedWeaponsResolver {

    private final String PROJECTILE_VELOCITY_MODIFIER_TAG = "projectileVelocityModifier";

    public boolean isCustomRangedWeapon(ItemStack item) {
        if (item == null || item.getType().equals(Material.AIR)) return false;
        NBTItem nbt = new NBTItem(item);
        return nbt.hasKey(PROJECTILE_VELOCITY_MODIFIER_TAG);
    }

    public float getProjectileVelocityModifier(ItemStack item) {
        NBTItem nbt = new NBTItem(item);
        return nbt.getFloat(PROJECTILE_VELOCITY_MODIFIER_TAG);
    }

    public void onProjectileShoot(Projectile projectile, ItemStack weapon) {
        float velocityModifier = this.getProjectileVelocityModifier(weapon);
        Vector velocity = projectile.getVelocity();
        projectile.setVelocity(velocity.multiply(velocityModifier));
    }
}
