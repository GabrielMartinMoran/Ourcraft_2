package com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.potions;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.CustomItem;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.meta.PotionMeta;

public class ManaPotion extends BaseManaPotion {

    @Override
    protected String getName() {
        return "Poción de mana";
    }

    @Override
    protected int getPotionLevel() {
        return 1;
    }

    @Override
    protected int getManaRecoverAmount() {
        return 20;
    }
}
