package com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.armors;

import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class CustomArmor {

    public static final String MAX_MANA_MODIFIER_TAG = "MaxManaModifier";

    private final double MIN_COPPER_COST = 10;
    public Material baseMaterial;
    HashMap<CustomArmorModifiers, Double> modifiers;

    public CustomArmor(Material baseMaterial, HashMap<CustomArmorModifiers, Double> modifiers) {
        this.baseMaterial = baseMaterial;
        this.modifiers = modifiers;
    }

    public ItemStack getItem() {
        ItemStack item = new ItemStack(this.baseMaterial);
        this.applyLore(item);
        this.applyAttributes(item);
        this.applyEnchantments(item);
        item = this.applyMaxManaModifier(item);
        return item;
    }

    public long calculateCopperCost() {
        double cost = this.getBaseMaterialCost() + this.getArmorModifierCost() + this.getArmorToughnessCost() +
                this.getMovementSpeedModifierCost() + this.getMaxHealthModifierCost() +
                this.getKnockbackResistanceModifierCost() + this.getManaModifierCost();
        if (this.hasModifier(CustomArmorModifiers.CURSE_OF_BINDING)) cost -= cost * 0.1;
        if (this.hasModifier(CustomArmorModifiers.CURSE_OF_VANISHING)) cost -= cost * 0.2;
        if (cost < MIN_COPPER_COST) cost = MIN_COPPER_COST;
        return (long) Math.ceil(cost);
    }

    private boolean hasModifier(CustomArmorModifiers modifier) {
        return this.modifiers.containsKey(modifier);
    }

    private double getBaseMaterialCost() {
        double materialMultiplier = 0d;
        double partMultiplier = 0d;
        String strMaterial = this.baseMaterial.toString();
        // Material del que esta construido
        if (strMaterial.contains("LEATHER")) materialMultiplier = 10d;
        if (strMaterial.contains("GOLDEN")) materialMultiplier = 30d;
        if (strMaterial.contains("CHAINMAIL")) materialMultiplier = 50d;
        if (strMaterial.contains("IRON")) materialMultiplier = 70d;
        if (strMaterial.contains("DIAMOND")) materialMultiplier = 90d;
        if (strMaterial.contains("NETHERITE")) materialMultiplier = 110d;
        // Parte de la armadura
        if (strMaterial.contains("BOOTS")) partMultiplier = 4d;
        if (strMaterial.contains("HELMET")) partMultiplier = 5d;
        if (strMaterial.contains("LEGGINGS")) partMultiplier = 7d;
        if (strMaterial.contains("CHESTPLATE")) partMultiplier = 8d;
        return materialMultiplier * partMultiplier;
    }

    private double getArmorModifierCost() {
        if (!this.hasModifier(CustomArmorModifiers.ARMOR)) return 0;
        double armorModifier = this.modifiers.get(CustomArmorModifiers.ARMOR);
        return Math.pow(2, armorModifier);
    }

    private double getArmorToughnessCost() {
        if (!this.hasModifier(CustomArmorModifiers.TOUGHNESS)) return 0;
        double armorToughnessModifier = this.modifiers.get(CustomArmorModifiers.TOUGHNESS);
        return Math.pow(4, armorToughnessModifier);
    }

    private double getMovementSpeedModifierCost() {
        if (!this.hasModifier(CustomArmorModifiers.MOVEMENT_SPEED)) return 0;
        double movementSpeedModifier = this.modifiers.get(CustomArmorModifiers.MOVEMENT_SPEED);
        return Math.pow(10, movementSpeedModifier * 10);
    }

    private double getMaxHealthModifierCost() {
        if (!this.hasModifier(CustomArmorModifiers.MAX_HEALTH)) return 0;
        double maxHealthModifier = this.modifiers.get(CustomArmorModifiers.MAX_HEALTH);
        return Math.pow(3, maxHealthModifier);
    }

    private double getKnockbackResistanceModifierCost() {
        if (!this.hasModifier(CustomArmorModifiers.KNOCKBACK_RESISTANCE)) return 0;
        double knockbackResistanceModifier = this.modifiers.get(CustomArmorModifiers.KNOCKBACK_RESISTANCE);
        return Math.pow(4, knockbackResistanceModifier * 10);
    }

    private double getManaModifierCost() {
        if (!this.hasModifier(CustomArmorModifiers.MAX_MANA)) return 0;
        double manaModifier =this.modifiers.get(CustomArmorModifiers.MAX_MANA);
        if (manaModifier > 0) return manaModifier * 2;
        return manaModifier;
    }

    private void applyAttributes(ItemStack item) {
        EquipmentSlot slot = this.getEquipmentSlot();
        ItemMeta meta = item.getItemMeta();
        if (this.hasModifier(CustomArmorModifiers.ARMOR))
            meta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(UUID.randomUUID(), Attribute.GENERIC_ARMOR.name(), this.modifiers.get(CustomArmorModifiers.ARMOR), AttributeModifier.Operation.ADD_NUMBER, slot));
        if (this.hasModifier(CustomArmorModifiers.TOUGHNESS))
            meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, new AttributeModifier(UUID.randomUUID(), Attribute.GENERIC_ARMOR_TOUGHNESS.name(), this.modifiers.get(CustomArmorModifiers.TOUGHNESS), AttributeModifier.Operation.ADD_NUMBER, slot));
        if (this.hasModifier(CustomArmorModifiers.MOVEMENT_SPEED))
            meta.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED, new AttributeModifier(UUID.randomUUID(), Attribute.GENERIC_MOVEMENT_SPEED.name(), this.modifiers.get(CustomArmorModifiers.MOVEMENT_SPEED), AttributeModifier.Operation.ADD_NUMBER, slot));
        if (this.hasModifier(CustomArmorModifiers.MAX_HEALTH))
            meta.addAttributeModifier(Attribute.GENERIC_MAX_HEALTH, new AttributeModifier(UUID.randomUUID(), Attribute.GENERIC_MAX_HEALTH.name(), this.modifiers.get(CustomArmorModifiers.MAX_HEALTH), AttributeModifier.Operation.ADD_NUMBER, slot));
        if (this.hasModifier(CustomArmorModifiers.KNOCKBACK_RESISTANCE))
            meta.addAttributeModifier(Attribute.GENERIC_KNOCKBACK_RESISTANCE, new AttributeModifier(UUID.randomUUID(), Attribute.GENERIC_KNOCKBACK_RESISTANCE.name(), this.modifiers.get(CustomArmorModifiers.KNOCKBACK_RESISTANCE), AttributeModifier.Operation.ADD_NUMBER, slot));
        item.setItemMeta(meta);
    }

    private EquipmentSlot getEquipmentSlot() {
        String strMaterial = this.baseMaterial.toString();
        if (strMaterial.contains("BOOTS")) return EquipmentSlot.FEET;
        if (strMaterial.contains("HELMET")) return EquipmentSlot.HEAD;
        if (strMaterial.contains("LEGGINGS")) return EquipmentSlot.LEGS;
        return EquipmentSlot.CHEST;
    }

    private void applyEnchantments(ItemStack item) {
        if (this.hasModifier(CustomArmorModifiers.CURSE_OF_BINDING)) item.addEnchantment(Enchantment.BINDING_CURSE, 1);
        if (this.hasModifier(CustomArmorModifiers.CURSE_OF_VANISHING))
            item.addEnchantment(Enchantment.VANISHING_CURSE, 1);
    }

    private ItemStack applyMaxManaModifier(ItemStack item) {
        if (!this.hasModifier(CustomArmorModifiers.MAX_MANA)) return item;
        NBTItem nbtItem = new NBTItem(item);
        nbtItem.setInteger(MAX_MANA_MODIFIER_TAG, (int) Math.round(this.modifiers.get(CustomArmorModifiers.MAX_MANA)));
        return nbtItem.getItem();
    }

    private void applyLore(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        ArrayList<String> lore = new ArrayList<String>();
        if (this.hasModifier(CustomArmorModifiers.MAX_MANA)) {
            int maxManaModifier = (int) Math.round(this.modifiers.get(CustomArmorModifiers.MAX_MANA));
            if (maxManaModifier > 0)
                lore.add("" + ChatColor.BLUE + "+" + Integer.toString(maxManaModifier) + " Max Maná");
            if (maxManaModifier < 0)
                lore.add("" + ChatColor.RED + Integer.toString(maxManaModifier) + " Max Maná");
        }
        if (lore.size() > 0) meta.setLore(lore);
        item.setItemMeta(meta);
    }
}
