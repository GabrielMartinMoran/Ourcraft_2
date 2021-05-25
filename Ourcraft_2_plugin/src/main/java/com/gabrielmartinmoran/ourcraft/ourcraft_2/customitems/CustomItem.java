package com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.spells.SpellTypes;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

public interface CustomItem {

    ItemStack getItem();

    Recipe getRecipe();
}
