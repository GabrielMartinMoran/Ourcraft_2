package com.gabrielmartinmoran.ourcraft.ourcraft_2.items.weapons.melee.greatsword;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.weapons.melee.MeleeWeapon;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.weapons.melee.MeleeWeaponCharacteristics;

import java.util.Arrays;
import java.util.List;

public abstract class BaseGreatSword extends MeleeWeapon {

    @Override
    public float getAttackSpeed() {
        return 1.2f;
    }

    @Override
    public float getKnockback() {
        return 0.2f;
    }

    @Override
    public List<MeleeWeaponCharacteristics> getCharacteristics() {
        return Arrays.asList(MeleeWeaponCharacteristics.TWO_HANDED);
    }
}
