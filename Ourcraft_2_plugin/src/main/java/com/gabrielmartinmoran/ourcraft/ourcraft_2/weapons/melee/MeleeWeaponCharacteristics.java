package com.gabrielmartinmoran.ourcraft.ourcraft_2.weapons.melee;

public enum MeleeWeaponCharacteristics {
    THROWABE("isThrowable", "Lanzable", true),
    TWO_HANDED("isTwoHanded", "A dos manos", false), // Otorga fatiga minera y debilidad si se tiene un objeto en la otra mano
    IMPRACTICAL("isImpractical", "Impráctica", false),
    LIFEDRAINER("isLifedrainer", "Robo de vida", true),
    INCAPACITATING("isIncapacitating", "Incapacitante", true),
    HEAVY("isHeavy", "Pesada", false),
    BLEEDING("isBleeding", "Desangrante", true),
    NUMBING("isNumbing", "Entumecedora", true),
    ;

    private String tag;
    private String displayName;
    private boolean isPositive;

    private MeleeWeaponCharacteristics(String tag, String displayName, boolean isPositive) {
        this.tag = tag;
        this.displayName = displayName;
        this.isPositive = isPositive;
    }

    public String getTag() {
        return this.tag;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public boolean isPositive() {
        return this.isPositive;
    }
}
