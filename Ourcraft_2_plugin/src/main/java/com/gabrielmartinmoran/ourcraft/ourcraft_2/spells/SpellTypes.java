package com.gabrielmartinmoran.ourcraft.ourcraft_2.spells;

public enum SpellTypes {
    NONE( 0),
    LIGHTNING( 1),
    NECROMANCER (2),
    HEALING (3),
    FIREBALL (4),
    LEVITATE (5),
    TELEPORT (6),
    SLOW_FALL (7),
    FIRE_RESISTANCE (8),
    MAGIC_ARROWS (9),
    MAGIC_MISSILES (10),
    POISON_CLOUD(11);

    private int id;

    public int getId()
    {
        return this.id;
    }

    public static SpellTypes fromInt(int id) {
        for (SpellTypes spellType : SpellTypes.values()) {
            if(spellType.getId() == id) return spellType;
        }
        return null;
    }

    // enum constructor - cannot be public or protected
    private SpellTypes(int id)
    {
        this.id = id;
    }
}
