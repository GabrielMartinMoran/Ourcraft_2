package com.gabrielmartinmoran.ourcraft.ourcraft_2.utils;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.spells.SpellTypes;

public class SpellsUtils {

    public static int calculateSpellBookManaCost(SpellTypes spellType, int level) {
        if (level == 1) return spellType.getBaseManaCost();
        return (int)(spellType.getBaseManaCost() * (level * (level - 1)));
    }


}
