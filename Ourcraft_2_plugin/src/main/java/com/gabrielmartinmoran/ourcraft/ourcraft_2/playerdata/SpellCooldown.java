package com.gabrielmartinmoran.ourcraft.ourcraft_2.playerdata;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.spells.SpellTypes;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class SpellTypeCooldown {

    private SpellTypes spellType;
    private LocalDateTime lastCast;

    public SpellTypeCooldown(SpellTypes spellType, LocalDateTime lastCast) {
        this.spellType = spellType;
        this.lastCast = lastCast;
    }

    public SpellTypes getSpellType() {
        return spellType;
    }

    public LocalDateTime getLastCast() {
        return lastCast;
    }

    public void resetLastCast() {
        this.lastCast = LocalDateTime.now();
    }

    public long getSecondsSinceLastCast() {
        return ChronoUnit.SECONDS.between(this.lastCast, LocalDateTime.now());
    }



}
