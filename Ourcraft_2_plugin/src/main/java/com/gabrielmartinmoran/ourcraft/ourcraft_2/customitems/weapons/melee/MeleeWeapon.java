package com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.weapons.melee;

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
import java.util.List;
import java.util.UUID;

public abstract class MeleeWeapon implements CustomItem {

    private final float BASE_ATTACK_SPEED = 4;

    public abstract Material getBaseMaterial();
    public abstract String getName();
    public abstract CustomItemsModelData getModelData();
    public abstract float getDamage();

    public float getThrowingDamage() {
        return 0f;
    }

    public float getAttackSpeed() {
        return 1.6f;
    }

    public List<MeleeWeaponCharacteristics> getCharacteristics() {
        return new ArrayList<>();
    }

    public float getMovementSpeedModifier() {
        return 0f;
    }

    public float getKnockback() {
        return 0f;
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
        nbtItem.setFloat("throwingDamage", this.getThrowingDamage());
        this.applyCharacteristics(nbtItem);
        return nbtItem.getItem();
    }

    protected void applyAttributes(ItemMeta meta) {
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, new AttributeModifier(UUID.fromString("9a050d06-472d-48f6-b4a6-de56cbe4c237"), Attribute.GENERIC_ATTACK_DAMAGE.name(), this.getDamage(), AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND));
        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, new AttributeModifier(UUID.fromString("2bd15849-52e6-40b5-807f-e19fee1dee97"), Attribute.GENERIC_ATTACK_SPEED.name(), this.calculateAttackSpeed(), AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND));
        meta.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED, new AttributeModifier(UUID.fromString("444f61ef-3aa2-4c54-9459-465af0e4d85d"), Attribute.GENERIC_MOVEMENT_SPEED.name(), this.getMovementSpeedModifier(), AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND));
        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_KNOCKBACK, new AttributeModifier(UUID.fromString("c015571d-6fe7-451a-a31b-cea89713e407"), Attribute.GENERIC_ATTACK_KNOCKBACK.name(), this.getKnockback(), AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND));
    }

    private void applyCharacteristics(NBTItem nbtItem) {
        List<MeleeWeaponCharacteristics> characteristics = this.getCharacteristics();
        for (MeleeWeaponCharacteristics characteristic: this.getCharacteristics()) {
            nbtItem.setBoolean(characteristic.getTag(), true);
        }
    }

    private List<String> generateLore() {
        ArrayList<String> lore = new ArrayList<String>();
        if(this.getLore() != null) {
            for (String line: this.getLore().split("\n")) {
                lore.add(line);
            }
        }
        for (MeleeWeaponCharacteristics characteristic: this.getCharacteristics()) {
            lore.add("" + ChatColor.GOLD + "- " + characteristic.getDisplayName());
        }
        if(lore.size() > 0) lore.add("");
        // Attributes section
        lore.add("" + ChatColor.GRAY + "When in Main Hand:");
        lore.add(" " + ChatColor.DARK_GREEN + this.getDamage() + " Attack Damage");
        if (this.getCharacteristics().contains(MeleeWeaponCharacteristics.THROWABE)) {
            lore.add(" " + ChatColor.DARK_GREEN + this.getThrowingDamage() + " Throwing Damage");
        }
        lore.add(" " + ChatColor.DARK_GREEN + this.getAttackSpeed() + " Attack Speed");
        if(this.getKnockback() > 0) {
            lore.add(" " + ChatColor.DARK_GREEN + this.getKnockback() + " Attack Knockback");
        } else if (this.getKnockback() < 0) {
            lore.add(" " + ChatColor.RED + this.getKnockback() + " Attack Knockback");
        }
        if(this.getMovementSpeedModifier() > 0) {
            lore.add(" " + ChatColor.DARK_GREEN + this.getMovementSpeedModifier() + " Movement Speed");
        } else if (this.getMovementSpeedModifier() < 0) {
            lore.add(" " + ChatColor.RED + this.getMovementSpeedModifier() + " Movement Speed");
        }
        return lore;
    }

    private float calculateAttackSpeed() {
        return this.getAttackSpeed() - BASE_ATTACK_SPEED;
    }
}
