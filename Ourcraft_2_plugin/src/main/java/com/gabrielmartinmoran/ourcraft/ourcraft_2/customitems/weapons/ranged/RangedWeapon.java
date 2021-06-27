package com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.weapons.ranged;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.CustomItem;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.CustomItemsModelData;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.weapons.melee.MeleeWeaponCharacteristics;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public abstract class RangedWeapon extends CustomItem {
    private final float BASE_ATTACK_SPEED = 4;

    public abstract Material getBaseMaterial();

    public abstract String getName();

    public abstract CustomItemsModelData getModelData();

    public float getMovementSpeedModifier() {
        return 0f;
    }

    public float getProjectileVelocityModifier() {
        return 1f;
    }

    /**
     * En caso de necesitar varias lineas utilizar '\n'
     */
    public String getLore() {
        return null;
    }

    @Override
    public ItemStack getItem() {
        ItemStack item = new ItemStack(this.getBaseMaterial());
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(this.getName());
        meta.setLore(this.generateLore());
        meta.setCustomModelData(this.getModelData().getModelData());
        this.applyAttributes(meta);
        item.setItemMeta(meta);
        NBTItem nbtItem = new NBTItem(item);
        nbtItem.setFloat("projectileVelocityModifier", this.getProjectileVelocityModifier());
        return nbtItem.getItem();
    }

    protected void applyAttributes(ItemMeta meta) {
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED, new AttributeModifier(UUID.fromString("444f61ef-3aa2-4c54-9459-465af0e4d85d"), Attribute.GENERIC_MOVEMENT_SPEED.name(), this.getMovementSpeedModifier(), AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND));
        meta.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED, new AttributeModifier(UUID.fromString("444f61ef-3aa2-4c54-9459-465af0e4d85e"), Attribute.GENERIC_MOVEMENT_SPEED.name(), this.getMovementSpeedModifier(), AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.OFF_HAND));
    }

    private List<String> generateLore() {
        ArrayList<String> lore = new ArrayList<String>();
        if (this.getLore() != null) {
            for (String line : this.getLore().split("\n")) {
                lore.add(line);
            }
        }
        // Attributes section
        if(this.getProjectileVelocityModifier() != 1 || this.getMovementSpeedModifier() != 0) {
            if (lore.size() > 0) lore.add("");
            lore.add("" + ChatColor.GRAY + "When in Hand:");
        }
        if (this.getProjectileVelocityModifier() > 1) {
            lore.add(" " + ChatColor.DARK_GREEN + this.getProjectileVelocityModifier() + " Projectile Velocity");
        } else if (this.getProjectileVelocityModifier() < 1) {
            lore.add(" " + ChatColor.RED + this.getProjectileVelocityModifier() + " Projectile Velocity");
        }
        if (this.getMovementSpeedModifier() > 0) {
            lore.add(" " + ChatColor.DARK_GREEN + this.getMovementSpeedModifier() + " Movement Speed");
        } else if (this.getMovementSpeedModifier() < 0) {
            lore.add(" " + ChatColor.RED + this.getMovementSpeedModifier() + " Movement Speed");
        }
        return lore;
    }
}