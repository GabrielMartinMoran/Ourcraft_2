package com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.armors;


import org.bukkit.Material;

import java.security.SecureRandom;
import java.util.*;

public class CustomArmorGenerator {

    private List<Material> armorsMaterials;
    private SecureRandom rand;
    private final double CURSE_OF_BINDING_MODIFIER_PROBABILITY = 0.2d;
    private final double CURSE_OF_VANISHING_MODIFIER_PROBABILITY = 0.15d;
    private final double ARMOR_MODIFIER_PROBABILITY = 0.8d;
    private final double TOUGHNESS_MODIFIER_PROBABILITY = 0.4d;
    private final double MOVEMENT_SPEED_MODIFIER_PROBABILITY = 0.3d;
    private final double MAX_HEALTH_MODIFIER_PROBABILITY = 0.5d;
    private final double KNOCKBACK_RESISTANCE_MODIFIER_PROBABILITY = 0.3d;
    private final double MAX_MANA_MODIFIER_PROBABILITY = 0.4d;

    public CustomArmorGenerator() {
        this.armorsMaterials = Arrays.asList(
                Material.LEATHER_BOOTS, Material.LEATHER_HELMET, Material.LEATHER_LEGGINGS, Material.LEATHER_CHESTPLATE,
                Material.GOLDEN_BOOTS, Material.GOLDEN_HELMET, Material.LEATHER_LEGGINGS, Material.GOLDEN_CHESTPLATE,
                Material.CHAINMAIL_BOOTS, Material.CHAINMAIL_HELMET, Material.CHAINMAIL_LEGGINGS, Material.CHAINMAIL_CHESTPLATE,
                Material.IRON_BOOTS, Material.IRON_HELMET, Material.IRON_LEGGINGS, Material.IRON_CHESTPLATE,
                Material.DIAMOND_BOOTS, Material.DIAMOND_HELMET, Material.DIAMOND_LEGGINGS, Material.DIAMOND_CHESTPLATE,
                Material.NETHERITE_BOOTS, Material.NETHERITE_HELMET, Material.NETHERITE_LEGGINGS, Material.NETHERITE_CHESTPLATE
        );
        this.rand = new SecureRandom();
    }

    public CustomArmor generateArmor() {
        Material baseMaterial = armorsMaterials.get(rand.nextInt(armorsMaterials.size()));
        // TODO: Agregar modificador de mana
        HashMap<CustomArmorModifiers, Double> modifiers = new HashMap<CustomArmorModifiers, Double>();
        if (this.rand.nextDouble() <= ARMOR_MODIFIER_PROBABILITY) modifiers.put(CustomArmorModifiers.ARMOR, this.getRandDoubleBetween(1d, 10d, 1));
        if (this.rand.nextDouble() <= TOUGHNESS_MODIFIER_PROBABILITY) modifiers.put(CustomArmorModifiers.TOUGHNESS, (double) this.rand.nextInt(5));
        if (this.rand.nextDouble() <= MOVEMENT_SPEED_MODIFIER_PROBABILITY) modifiers.put(CustomArmorModifiers.MOVEMENT_SPEED, this.getRandDoubleBetween(-0.05, 0.05, 2));
        if (this.rand.nextDouble() <= MAX_HEALTH_MODIFIER_PROBABILITY) modifiers.put(CustomArmorModifiers.MAX_HEALTH, (double) this.getRandIntBetween(-10, 10));
        if (this.rand.nextDouble() <= KNOCKBACK_RESISTANCE_MODIFIER_PROBABILITY) modifiers.put(CustomArmorModifiers.KNOCKBACK_RESISTANCE, this.getRandDoubleBetween(0d, 0.8d, 1));
        if (this.rand.nextDouble() <= CURSE_OF_BINDING_MODIFIER_PROBABILITY) modifiers.put(CustomArmorModifiers.CURSE_OF_BINDING, 1d);
        if (this.rand.nextDouble() <= CURSE_OF_VANISHING_MODIFIER_PROBABILITY) modifiers.put(CustomArmorModifiers.CURSE_OF_VANISHING, 1d);
        if (this.rand.nextDouble() <= MAX_MANA_MODIFIER_PROBABILITY) modifiers.put(CustomArmorModifiers.MAX_MANA, (double) this.getRandIntBetween(-500, 500));
        return new CustomArmor(baseMaterial, modifiers);
    }

    private double getRandDoubleBetween(double min, double max, double decimals) {
        double randomValue = min + (max - min) * this.rand.nextDouble();
        double scale = Math.pow(10, decimals);
        return Math.round(randomValue * scale) / scale;
    }

    private int getRandIntBetween(int min, int max) {
        return this.rand.nextInt(max + 1 - min) + min;
    }
}
