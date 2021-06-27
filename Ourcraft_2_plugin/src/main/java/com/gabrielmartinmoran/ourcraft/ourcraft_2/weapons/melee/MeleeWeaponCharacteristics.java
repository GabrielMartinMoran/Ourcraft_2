package com.gabrielmartinmoran.ourcraft.ourcraft_2.weapons.melee;

public enum MeleeWeaponCharacteristics {
    THROWABE("isThrowable", "Lanzable"),
    TWO_HANDED("isTwoHanded", "A dos manos"), // Otorga fatiga minera y debilidad si se tiene un objeto en la otra mano
    IMPRACTICAL("isImpractical", "Impráctica");

    private String tag;
    private String displayName;

    private MeleeWeaponCharacteristics(String tag, String displayName) {
        this.tag = tag;
        this.displayName = displayName;
    }

    public String getTag() {
        return this.tag;
    }

    public String getDisplayName() {
        return this.displayName;
    }
}
