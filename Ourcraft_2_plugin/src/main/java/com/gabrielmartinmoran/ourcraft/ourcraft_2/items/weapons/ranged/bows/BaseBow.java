package com.gabrielmartinmoran.ourcraft.ourcraft_2.items.weapons.ranged.bows;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.weapons.ranged.RangedWeapon;
import org.bukkit.Material;

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
