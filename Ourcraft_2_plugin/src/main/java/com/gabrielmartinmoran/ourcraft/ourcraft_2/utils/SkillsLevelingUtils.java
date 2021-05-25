package com.gabrielmartinmoran.ourcraft.ourcraft_2.utils;

public class HabilityLevelingUtils {

    private static final int BASE_LEVELING_XP = 5;
    private static final int LEVELING_FACTOR = 3;

    public static int getXPRequiredForHabilityLevel(int level) {
        if (level == 1) return 0;
        return (int)(BASE_LEVELING_XP * Math.pow(level, LEVELING_FACTOR));
    }
}
