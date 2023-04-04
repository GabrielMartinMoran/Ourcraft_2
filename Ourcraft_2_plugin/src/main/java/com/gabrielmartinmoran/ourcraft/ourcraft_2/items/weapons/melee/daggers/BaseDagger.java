package com.gabrielmartinmoran.ourcraft.ourcraft_2.items.weapons.melee.daggers;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.weapons.melee.MeleeWeapon;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.weapons.melee.MeleeWeaponCharacteristics;

import java.util.Arrays;
import java.util.List;

public abstract class BaseDagger extends MeleeWeapon {

    @Override
    public float getAttackSpeed() {
        return 2f;
    }

    @Override
    public List<MeleeWeaponCharacteristics> getCharacteristics() {
        return Arrays.asList(MeleeWeaponCharacteristics.THROWABE);
    }

    @Override
    public float getKnockback() {
        return -0.5f;
    }

    @Override
    public float getThrowingDamage() { return this.getDamage(); }
}
