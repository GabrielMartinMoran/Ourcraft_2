package com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.weapons.ranged.bows;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.weapons.melee.MeleeWeapon;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.weapons.ranged.RangedWeapon;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.weapons.melee.MeleeWeaponCharacteristics;
import org.bukkit.Material;

import java.util.Arrays;
import java.util.List;

public abstract class BaseBow extends RangedWeapon {

    @Override
    public float getProjectileVelocityModifier() {
        return 1f;
    }

    @Override
    public Material getBaseMaterial() {
        return Material.BOW;
    }

}
