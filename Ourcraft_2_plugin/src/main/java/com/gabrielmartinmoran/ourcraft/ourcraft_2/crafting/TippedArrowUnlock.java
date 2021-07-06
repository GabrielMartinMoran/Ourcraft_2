package com.gabrielmartinmoran.ourcraft.ourcraft_2.crafting;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.utils.TippedArrowsHelper;
import org.bukkit.potion.PotionEffectType;

public class TippedArrowUnlock {

    public PotionEffectType effect;
    public int amplifier;
    public int unlockLevel;

    public TippedArrowUnlock(PotionEffectType effect, int amplifier, int unlockLevel) {
        this.effect = effect;
        this.amplifier = amplifier;
        this.unlockLevel = unlockLevel;
    }

    public String getUnlockName() {
        return TippedArrowsHelper.getArrowName(effect, amplifier);
    }
}
