package com.gabrielmartinmoran.ourcraft.ourcraft_2;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.crafting.TippedArrowUnlock;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;
import java.util.List;

public class Config {
    public static final int WORLD_RADIUS = 10000;
    public static final int MAX_DIFFICULTY_ZONES = 5;
    public static final int INITIAL_MANA = 10;
    public static final double MANA_INCREASE_POW_FACTOR = 1.68d;
    public static final int INITIAL_MANA_RECOVER = 1;
    public static final int MAX_ATTRIBUTE_LEVEL = 100;
    public static final int TICKS_PER_SECOND = 20;
    public static final double SWIMMING_BLOCK_XP = 0.1d;
    public static final double WALKING_BLOCK_XP = 0.05d;
    public static final double BREEDING_XP = 5d;
    public static final double SPELL_CAST_XP_MULTIPLIER = 5d;
    public static final double SPELL_CAST_XP_POW_FACTOR = 1.5d;
    public static final double PORTAl_CREATION_MAX_DETECTION_RANGE = 5d;
    public static final int NETHER_PORTAL_ACTIVATION_MAGIC_MIN_LEVEL = 32;
    public static final int TELEPORTER_CREATION_MAGIC_MIN_LEVEL = 75;

    // Niveles de flechas magicas
    public static List<TippedArrowUnlock> TIPPED_ARROWS_UNLOCKS = Arrays.asList(
            // Level 1 de pocion
            new TippedArrowUnlock(PotionEffectType.NIGHT_VISION, 0, 37),
            new TippedArrowUnlock(PotionEffectType.INVISIBILITY, 0, 45),
            new TippedArrowUnlock(PotionEffectType.JUMP, 0, 48),
            new TippedArrowUnlock(PotionEffectType.SPEED, 0, 52),
            new TippedArrowUnlock(PotionEffectType.FIRE_RESISTANCE, 0, 54),
            new TippedArrowUnlock(PotionEffectType.HEAL, 0, 56),
            new TippedArrowUnlock(PotionEffectType.REGENERATION, 0, 58),
            new TippedArrowUnlock(PotionEffectType.INCREASE_DAMAGE, 0, 62),
            new TippedArrowUnlock(PotionEffectType.WEAKNESS, 0, 64),
            new TippedArrowUnlock(PotionEffectType.SLOW, 0, 66),
            new TippedArrowUnlock(PotionEffectType.SLOW_FALLING, 0, 68),
            new TippedArrowUnlock(PotionEffectType.WATER_BREATHING, 0, 72),
            new TippedArrowUnlock(PotionEffectType.POISON, 0, 74),
            new TippedArrowUnlock(PotionEffectType.HARM, 0, 76),
            // Level 2 de pocion
            new TippedArrowUnlock(PotionEffectType.JUMP, 1, 78),
            new TippedArrowUnlock(PotionEffectType.SPEED, 1, 82),
            new TippedArrowUnlock(PotionEffectType.HEAL, 1, 84),
            new TippedArrowUnlock(PotionEffectType.REGENERATION, 1, 86),
            new TippedArrowUnlock(PotionEffectType.INCREASE_DAMAGE, 1, 88),
            new TippedArrowUnlock(PotionEffectType.WEAKNESS, 1, 90),
            new TippedArrowUnlock(PotionEffectType.SLOW, 1, 92),
            new TippedArrowUnlock(PotionEffectType.SLOW_FALLING, 1, 94),
            new TippedArrowUnlock(PotionEffectType.POISON, 1, 96),
            new TippedArrowUnlock(PotionEffectType.HARM, 1, 98)
    );
}
