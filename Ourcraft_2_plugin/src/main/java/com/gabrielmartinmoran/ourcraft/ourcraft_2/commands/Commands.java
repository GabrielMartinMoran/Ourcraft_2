package com.gabrielmartinmoran.ourcraft.ourcraft_2.commands;

public enum Commands {
    SPELLBOOK("spellbook"),
    DEBUG("debug"),
    STATS("stats"),
    PLAYERDATA("playerdata"),
    MEMORIES("memories"),
    DIFFICULTYZONE("difficultyzone");

    private String command;

    private Commands(String command) {
        this.command = command;
    }

    @Override
    public java.lang.String toString() {
        return this.command;
    }
}
