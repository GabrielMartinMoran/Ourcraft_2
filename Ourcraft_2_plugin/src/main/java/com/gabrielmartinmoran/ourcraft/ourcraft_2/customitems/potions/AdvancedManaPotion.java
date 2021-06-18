package com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.potions;

public class RegularManaPotion extends BaseManaPotion {

    @Override
    protected String getName() {
        return "Poción de mana regular";
    }

    @Override
    protected int getManaRecoverAmount() {
        return 50;
    }
}
