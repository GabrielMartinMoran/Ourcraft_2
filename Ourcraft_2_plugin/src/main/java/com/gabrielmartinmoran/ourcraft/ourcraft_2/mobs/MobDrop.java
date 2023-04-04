package com.gabrielmartinmoran.ourcraft.ourcraft_2.mobs;

import org.bukkit.inventory.ItemStack;

public class MobDrop {
    public int weight;
    public int value;
    public int min;
    public int max;
    public int amount;
    public ItemStack item;

    public MobDrop(int weight, int value, ItemStack item, int min, int max) {
        this.weight = weight;
        this.value = value;
        this.min = min;
        this.max = max;
        this.item = item;
    }

    public MobDrop clone() {
        return new MobDrop(this.weight, this.value, this.item, this.min, this.max);
    }
}
