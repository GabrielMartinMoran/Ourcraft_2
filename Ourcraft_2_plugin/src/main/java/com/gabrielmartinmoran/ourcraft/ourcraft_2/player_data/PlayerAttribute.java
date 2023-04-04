package com.gabrielmartinmoran.ourcraft.ourcraft_2.player_data;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.utils.SkillsLevelingUtils;

public class PlayerAttribute {

    private PlayerAttributes attribute;
    private double xp;
    private int level;

    public PlayerAttribute(PlayerAttributes attribute) {
        this.attribute = attribute;
        this.xp = 0;
        this.level = 1;
    }

    /**
     * @param amount
     * @return true when player has leveled up attribute
     */
    public boolean addXp(double amount) {
        this.xp += amount;
        if (SkillsLevelingUtils.getXPRequiredForHabilityLevel(this.level + 1) <= this.xp) {
            this.level++;
            return true;
        }
        return false;
    }

    public double getXp() {
        return this.xp;
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int level) {
        this.xp = SkillsLevelingUtils.getXPRequiredForHabilityLevel(level);
        this.level = level;
    }
}
