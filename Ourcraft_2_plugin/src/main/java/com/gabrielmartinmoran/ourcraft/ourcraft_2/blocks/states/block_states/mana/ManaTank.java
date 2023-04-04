package com.gabrielmartinmoran.ourcraft.ourcraft_2.blocks.states.block_states.mana;

public class ManaTank {
    private int maxMana;
    private int currentMana;

    public ManaTank(int maxMana, int currentMana) {
        this.maxMana = maxMana;
        this.currentMana = currentMana;
    }

    public int getMaxMana() {
        return this.maxMana;
    }

    public int getCurrentMana() {
        return this.currentMana;
    }

    public void add(int amount) {
        this.currentMana += amount;
        this.currentMana = Math.min(this.currentMana, this.maxMana);
    }

    public void subtract(int amount) {
        this.currentMana -= amount;
        this.currentMana = Math.max(this.currentMana, 0);
    }

    public int subtractAllPossible(int maxAmount) {
        int consumed = Math.min(maxAmount, this.currentMana);
        this.subtract(consumed);
        return consumed;
    }

    public int getSpace() {
        return this.maxMana - this.currentMana;
    }

    public boolean hasEnough(int amount) {
        return this.currentMana >= amount;
    }

    public boolean hasSpace() {
        return this.getSpace() > 0;
    }
}
