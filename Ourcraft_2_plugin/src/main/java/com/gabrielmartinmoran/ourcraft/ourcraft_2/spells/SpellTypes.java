package com.gabrielmartinmoran.ourcraft.ourcraft_2.spells;

import org.bukkit.ChatColor;

public enum SpellTypes {

    // Agregar cooldown
    // Agregar conjuros basicos
    NONE(0, 0, "Ninguno", ChatColor.WHITE, ""),
    LIGHTNING(1, 40, "Rayo", ChatColor.AQUA, "Invoca rayos que caen en el objetivo"),
    NECROMANCER(2, 80, "Nigromancia", ChatColor.DARK_GRAY, "Invoca esqueletos wither que atacan al objetivo"),
    HEALING(3, 60, "Área sanadora", ChatColor.LIGHT_PURPLE, "Cura a todos los jugadores que se encuentren a pocos\nbloques de quien lanza el conjuro"),
    FIREBALL(4, 40, "Bola de fuego", ChatColor.RED, "Invoca una bola de fuego que arremete contra el objetivo"),
    LEVITATE(5, 60, "Levitar", ChatColor.WHITE, "Hace levitar en el aire a quien lanza el conjuro"),
    TELEPORT(6, 80, "Teletransporte", ChatColor.DARK_PURPLE, "Teletransporta al usuario cierta cantidad de\nbloques hacia adelante"),
    SLOW_FALL(7, 60, "Caída lenta", ChatColor.WHITE, "Hace caer suavemente a quien lanza el conjuro"),
    FIRE_RESISTANCE(8, 80, "Resistencia ignea", ChatColor.DARK_RED, "Hace resistir al fuego a quien lanza el conjuro"),
    MAGIC_ARROWS(9, 80, "Flechas mágicas", ChatColor.YELLOW, "Lanza flechas mágicas al objetivo"),
    MAGIC_MISSILES(10, 80, "Misiles mágicos", ChatColor.BLUE, "Lanza misiles mágicos que siguen al objetivo"),
    POISON_CLOUD(11, 80, "Nube venenosa", ChatColor.GREEN, "Lanza un orbe que genera una nube venenosa al impactar"),
    MAKE_LEVITATE(12, 60, "Hacer levitar", ChatColor.WHITE, "Lanza un orbe que hace levitar a quien golpea"),
    NAUSEATING_ORB(13, 40, "Orbe nauseabundo", ChatColor.GREEN, "Lanza un orbe que produce nauseas a quien golpea"),
    BLINDNESS_ORB(14, 80, "Orbe cegador", ChatColor.DARK_GRAY, "Lanza un orbe que produce ceguera a quien golpea"),
    REALENTIZING_ORB(15, 80, "Orbe realentizante", ChatColor.GRAY, "Lanza un orbe que produce realentiza a quien golpea"),
    EXPLODING_ORB(16, 80, "Orbe explosivo", ChatColor.DARK_RED, "Lanza un orbe que explota al colisionar"),
    GOOD_LUCK(17, 60, "Buena fortuna", ChatColor.GREEN, "Otorga buena suerte a quien lanza el conjuro"),
    HASTE(18, 60, "Prisa minera", ChatColor.GOLD, "Otorga velocidad al minar a quien lanza el conjuro"),
    MAGIC_SHOT(19, 10, "Disparo mágico", ChatColor.DARK_AQUA, "Dispara un proyectil mágico"),
    FIRE_RETARDANT_EFFORD(20, 20, "Esfuerzo ignífugo", ChatColor.RED, "Hace invulnerable al fuego a quien\nlanza el conjuro pero reduce la vitalidad\nprogresivamente"),
    EXPLOSION(21, 20, "Explosión", ChatColor.DARK_RED, "Generas una explosión de energía que brota desde tu cuerpo"),
    SPEED_UP(22, 60, "Velocidad", ChatColor.WHITE, "Otorga velocidad de movimiento a quien lanza el conjuro"),
    SPEED_UP_EFFORD(23, 20, "Esfuerzo ágil", ChatColor.WHITE, "Otorga velocidad a quien lanza el conjuro pero reduce\nla vitalidad progresivamente"),
    BURNING_ORB(24, 20, "Orbe llameante", ChatColor.RED, "Lanza un orbe que prende fuego a la criatura contra la que\nimpacte"),
    FAKE_LIFE(25, 60, "Falsa vida", ChatColor.GOLD, "Otorga absorción a quien lanza el conjuro"),
    SELF_HEALING(26, 80, "Curación", ChatColor.LIGHT_PURPLE, "Cura a quien lanza el conjuro"),
    CLEAN_STATUSES(27, 80, "Limpiar estados", ChatColor.GRAY, "Remueve los estados alterados de quien lanza el conjuro.\nA medida que aumenta de nivel, solo remueve estados negativos");
    // Nightvision
    // Curacion individual
    // Regeneracion y fuerza invocando deidad con probabilidad de fallo

    private final double BASE_SPELL_COOLDOWN = 5d;
    private final double SPELL_COOLDOWN_MULTIPLIER_BY_LEVEL = 0.6d;

    private int id;
    private int baseManaCost;
    private String name;
    private ChatColor nameColor;
    private String lore;

    private SpellTypes(int id, int baseManaCost, String name, ChatColor nameColor, String lore) {
        this.id = id;
        this.baseManaCost = baseManaCost;
        this.name = name;
        this.nameColor = nameColor;
        this.lore = lore;
    }

    public int getId() {
        return this.id;
    }

    public int getBaseManaCost() {
        return this.baseManaCost;
    }

    public String getName() {
        return this.name;
    }

    public ChatColor getNameColor() {
        return this.nameColor;
    }

    public String getLore() {
        return this.lore;
    }

    public static SpellTypes fromInt(int id) {
        for (SpellTypes spellType : SpellTypes.values()) {
            if (spellType.getId() == id) return spellType;
        }
        return null;
    }

    public double getCooldownByLevel(int level) {
        if (level == 1) return this.BASE_SPELL_COOLDOWN;
        return this.BASE_SPELL_COOLDOWN * (this.SPELL_COOLDOWN_MULTIPLIER_BY_LEVEL * level);
    }

}