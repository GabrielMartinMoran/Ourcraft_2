package com.gabrielmartinmoran.ourcraft.ourcraft_2.utils;

import java.util.HashMap;

public class SkillsLevelingUtils {

    private static final int BASE_LEVELING_XP = 5;
    private static final int LEVELING_FACTOR = 2;

    private static HashMap<Integer, Integer> cachedXPRequired = new HashMap<Integer, Integer>();

    public static int getXPRequiredForHabilityLevel(int level) {
        if(cachedXPRequired.containsKey(level)) return cachedXPRequired.get(level);
        if (level == 1) return 0;
        int result = (int)(BASE_LEVELING_XP * Math.pow(level, LEVELING_FACTOR));
        cachedXPRequired.put(level, result);
        return result;
    }
}
