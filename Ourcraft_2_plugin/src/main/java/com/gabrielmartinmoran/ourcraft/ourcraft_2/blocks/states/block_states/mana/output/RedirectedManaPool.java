package com.gabrielmartinmoran.ourcraft.ourcraft_2.blocks.states.block_states.mana.output;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.blocks.states.block_states.mana.ManaTank;
import jline.internal.Nullable;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

public class RedirectedManaPool implements ManaExporter {

    String name;
    Location location;
    ManaExporter manaExporter;
    @Nullable
    ItemStack transmissionChannel;

    public RedirectedManaPool(String name, Location location) {
        this.name = name;
        this.location = location;
    }

    @Override
    public void setTransmissionChannel(@Nullable ItemStack transmissionChannel) {
        this.transmissionChannel = transmissionChannel;
    }

    @Override
    public ItemStack getTransmissionChannel() {
        return this.transmissionChannel;
    }

    @Override
    public void consume(int amount) {
        if (this.manaExporter == null) return;
        this.manaExporter.consume(amount);
    }

    @Override
    public int consumeAllPossible(int maxAmount) {
        if (this.manaExporter == null) return 0;
        return this.manaExporter.consumeAllPossible(maxAmount);
    }

    @Override
    public boolean hasEnough(int amount) {
        if (this.manaExporter == null) return false;
        return this.manaExporter.hasEnough(amount);
    }

    @Override
    public int getCurrent() {
        if (this.manaExporter == null) return 0;
        return this.manaExporter.getCurrent();
    }

    @Override
    public int getSpace() {
        if (this.manaExporter == null) return 0;
        return this.manaExporter.getSpace();
    }

    @Override
    public boolean hasSpace() {
        if (this.manaExporter == null) return false;
        return this.manaExporter.hasSpace();
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Location getLocation() {
        return this.location;
    }

    @Override
    @Nullable
    public ManaTank getTank() {
        return null;
    }

    public void setManaExporter(@Nullable ManaExporter manaExporter) {
        this.manaExporter = manaExporter;
    }
}
