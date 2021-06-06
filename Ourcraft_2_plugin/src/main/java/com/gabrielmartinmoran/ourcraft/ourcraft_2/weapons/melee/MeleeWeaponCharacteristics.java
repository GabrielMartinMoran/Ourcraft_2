package com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.weapons.melee;

public enum MeleeWeaponCharacteristics {
    THROWABE("isThrowable"),
    TWO_HANDED("isTwoHanded"); // Otorga fatiga minera y debilidad si se tiene un objeto en la otra mano

    private String tag;

    private MeleeWeaponCharacteristics(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return this.tag;
    }
}
