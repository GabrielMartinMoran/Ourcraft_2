package com.gabrielmartinmoran.ourcraft.ourcraft_2.hydration;

public enum HydrationDecreaseEvents {
    WALK(0.002),
    CRAFT(0.02),
    SPRINT(0.004),
    JUMP(0.2),
    SWIM(0.01),
    BLOCK_BREAK(0.02),
    ATTACK(0.06),
    ATTACK_NO_WEAPON(0.03),
    REGEN_HEALTH(0.25);

    private double cost;

    private HydrationDecreaseEvents(double cost) {
        this.cost = cost;
    }

    public double getCost() {
        return this.cost;
    }
}
