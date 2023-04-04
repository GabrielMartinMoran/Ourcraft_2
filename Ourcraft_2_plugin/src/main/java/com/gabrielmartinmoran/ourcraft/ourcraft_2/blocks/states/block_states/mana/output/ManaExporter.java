package com.gabrielmartinmoran.ourcraft.ourcraft_2.blocks.states.block_states.mana.output;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.blocks.states.block_states.mana.ManaTank;
import jline.internal.Nullable;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

public interface ManaExporter {

    public void setTransmissionChannel(@Nullable ItemStack transmissionChannel);

    @Nullable
    public ItemStack getTransmissionChannel();

    public void consume(int amount);

    public int consumeAllPossible(int maxAmount);

    public boolean hasEnough(int amount);

    public int getCurrent();

    public int getSpace();

    public boolean hasSpace();

    public String getName();

    public Location getLocation();

    @Nullable
    public ManaTank getTank();
}
