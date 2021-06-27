package com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.weapons.melee.daggers;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.weapons.melee.MeleeWeapon;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.weapons.melee.MeleeWeaponCharacteristics;
import org.bukkit.inventory.ItemStack;

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
