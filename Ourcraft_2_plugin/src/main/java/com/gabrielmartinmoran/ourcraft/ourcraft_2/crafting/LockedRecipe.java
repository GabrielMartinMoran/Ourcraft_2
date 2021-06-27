package com.gabrielmartinmoran.ourcraft.ourcraft_2.crafting;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.playerdata.PlayerAttributes;

public class LockedRecipe {

    private PlayerAttributes attribute;
    private int minLevel;
    private String recipeName;

    public LockedRecipe(PlayerAttributes attribute, int minLevel, String recipeName) {
        this.attribute = attribute;
        this.minLevel = minLevel;
        this.recipeName = recipeName;
    }

    public PlayerAttributes getAttribute() {
        return attribute;
    }

    public int getMinLevel() {
        return minLevel;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public boolean compareName(String otherRecipeName) {
        return this.recipeName.equals(otherRecipeName);
    }
}
