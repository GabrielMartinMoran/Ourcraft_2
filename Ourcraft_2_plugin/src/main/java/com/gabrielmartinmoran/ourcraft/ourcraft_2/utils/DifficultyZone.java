package com.gabrielmartinmoran.ourcraft.ourcraft_2.utils;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.Config;
import org.bukkit.Location;

public class DifficultyZone {

    public static int calculateDifficultyZone(Location location) {
        double locRadius = Math.sqrt(Math.pow(location.getX(), 2) + Math.pow(location.getZ(), 2));
        double difficultyZonePercentage = locRadius / (double)Config.WORLD_RADIUS;// De 0 a 10
        int difficultyZone = (int) Math.ceil(Config.MAX_DIFFICULTY_ZONES * difficultyZonePercentage);
        if (difficultyZone == 0) return 1;
        if (difficultyZone > Config.MAX_DIFFICULTY_ZONES) return Config.MAX_DIFFICULTY_ZONES;
        return difficultyZone;
    }
}
