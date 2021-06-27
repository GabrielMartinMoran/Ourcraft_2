package com.gabrielmartinmoran.ourcraft.ourcraft_2.playerdata;

public enum PlayerAttributes {
    MAGIC("Magia"),
    MELEE("Combate cuerpo a cuerpo"),
    RANGED("Combate a distancia"),
    RESISTANCE("Resistencia"),
    CARPENTRY("Carpintería"),
    MINING("Minería"),
    FARMING("Granja");

    private String displayName;

    private PlayerAttributes(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return this.displayName;
    }
}
