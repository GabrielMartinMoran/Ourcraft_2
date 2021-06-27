package com.gabrielmartinmoran.ourcraft.ourcraft_2.hydration;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.Config;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

public class PlayerHydration {

    private final int MAX_HYDRATION = 12;
    private final int MAX_DISPLAYABLE_HYDRATION = 10;
    private final double STAT_CONSUMPTION_FACTOR = 0.2;
    private final int MINE_CONSUMPTION_FACTOR = 3;
    private final int SWIM_CONSUMPTION_FACTOR = 3;
    private final int WALK_CONSUMPTION_FACTOR = 1;
    private final int SPRINT_CONSUMPTION_FACTOR = 2;
    private final int JUMP_CONSUMPTION_FACTOR = 2;

    private double hydration;
    private long consumed;
    private long drinked;

    public PlayerHydration() {
        this.hydration = MAX_HYDRATION;
        this.addHydration(MAX_HYDRATION);
    }

    public void addHydration(double amount) {
        this.hydration += amount;
        if (this.hydration > MAX_HYDRATION) this.hydration = MAX_HYDRATION;
        if (this.hydration < 0) this.hydration = 0;
    }

    // Devuelve un numero entero entre 0 y 10
    public int getHydrationLevel() {
        double displayableHydration = this.hydration > MAX_DISPLAYABLE_HYDRATION ? MAX_DISPLAYABLE_HYDRATION : this.hydration;
        return (int)Math.round(displayableHydration);
    }

    public void consumeHydration(HydrationDecreaseEvents event) {
        this.hydration -= event.getCost();
        if (this.hydration < 0) this.hydration = 0;
    }

    public void fillHydrationLevel() {
        this.hydration = MAX_HYDRATION;
    }

    public void handleNotPurifiedWaterDrinking(Player player) {
        if((new Random()).nextDouble() > 0.6d) return;
        player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 10 * Config.TICKS_PER_SECOND, 0));
    }
}
