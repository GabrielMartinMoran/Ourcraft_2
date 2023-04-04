package com.gabrielmartinmoran.ourcraft.ourcraft_2.blocks.states.block_states.mana.machine_types;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.blocks.states.block_states.mana.ManaTank;
import jline.internal.Nullable;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public interface ManaMachine {
    public Inventory getInventory();

    @Nullable
    public ManaTank getTank();

    public String getName();


    public void setInventory(Inventory inventory);

    public ItemStack[] getValidBuffs();
}
