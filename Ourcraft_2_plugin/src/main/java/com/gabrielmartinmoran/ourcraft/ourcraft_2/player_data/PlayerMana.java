package com.gabrielmartinmoran.ourcraft.ourcraft_2.player_data;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.Config;

public class PlayerMana {
    private int maxMana;
    private int currentMana;
    private int manaRecover;
    private int maxManaModifier;

    public PlayerMana() {
        this.maxMana = Config.INITIAL_MANA;
        this.currentMana = Config.INITIAL_MANA;
        this.manaRecover = Config.INITIAL_MANA_RECOVER;
        this.maxManaModifier = 0;
    }

    public int getMana() {
        return this.currentMana;
    }

    public void recoverMana() {
        this.recoverMana(this.manaRecover);
    }

    public int getMaxMana() {
        int maxMana = this.maxMana + this.maxManaModifier;
        if (maxMana < 0) maxMana = 0;
        return maxMana;
    }

    public void recoverMana(int amount) {
        this.currentMana += amount;
        if (this.currentMana > this.getMaxMana()) this.currentMana = this.getMaxMana();
    }

    public void recoverAllMana() {
        this.currentMana = this.getMaxMana();
    }

    public void spendMana(int amount) {
        this.currentMana -= amount;
        if (this.currentMana < 0) this.currentMana = 0;
    }

    public void setMaxMana(int amount) {
        this.maxMana = amount;
    }

    public void setManaRecover(int amount) {
        this.manaRecover = amount;
    }

    public void setMaxManaModifier(int amount) {
        this.maxManaModifier = amount;
    }
}

