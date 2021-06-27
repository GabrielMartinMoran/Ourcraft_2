package com.gabrielmartinmoran.ourcraft.ourcraft_2.playerdata;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.spells.SpellTypes;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class SpellCooldown {

    private SpellTypes spellType;
    private LocalDateTime lastCast;
    private double cooldownSeconds;

    public SpellCooldown(SpellTypes spellType) {
        this.spellType = spellType;
    }

    public void resetCooldown(int level) {
        this.lastCast = LocalDateTime.now();
        this.cooldownSeconds = this.spellType.getCooldownByLevel(level);
    }

    public boolean isReady() {
        return ChronoUnit.SECONDS.between(this.lastCast, LocalDateTime.now()) >= this.cooldownSeconds;
    }

    public long getPendingCooldownSeconds() {
        long diff = (long) (this.cooldownSeconds - ChronoUnit.SECONDS.between(this.lastCast, LocalDateTime.now()));
        if(diff < 0) return 0;
        return diff;
    }
}
