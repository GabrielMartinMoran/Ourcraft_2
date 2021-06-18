package com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.potions;

public class AdvancedManaPotion extends BaseManaPotion {

    @Override
    protected String getName() {
        return "Poción de mana avanzada";
    }

    @Override
    protected int getManaRecoverAmount() {
        return 150;
    }
}
