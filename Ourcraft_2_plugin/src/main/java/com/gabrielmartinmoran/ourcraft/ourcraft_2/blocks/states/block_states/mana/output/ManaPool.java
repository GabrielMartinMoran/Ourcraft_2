package com.gabrielmartinmoran.ourcraft.ourcraft_2.blocks.states.block_states.mana.output;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.blocks.states.block_states.mana.ManaTank;
import jline.internal.Nullable;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

public class ManaPool implements ManaExporter {
    private String name;
    private Location location;
    private ManaTank tank;
    @Nullable
    private ItemStack transmissionChannel;

    public ManaPool(String name, Location location, ManaTank tank) {
        this.name = name;
        this.location = location;
        this.tank = tank;
    }

    @Override
    public void setTransmissionChannel(@Nullable ItemStack transmissionChannel) {
        this.transmissionChannel = transmissionChannel;
    }

    @Override
    @Nullable
    public ItemStack getTransmissionChannel() {
        return this.transmissionChannel;
    }

    @Override
    public void consume(int amount) {
        this.tank.subtract(amount);
    }

    @Override
    public int consumeAllPossible(int maxAmount) {
        return this.tank.subtractAllPossible(maxAmount);
    }

    @Override
    public boolean hasEnough(int amount) {
        return this.tank.hasEnough(amount);
    }

    @Override
    public int getCurrent() {
        return this.tank.getCurrentMana();
    }

    @Override
    public int getSpace() {
        return this.tank.getSpace();
    }

    @Override
    public boolean hasSpace() {
        return this.tank.hasSpace();
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
        return this.tank;
    }

    public void store(int amount) {
        this.tank.add(amount);
    }
}
