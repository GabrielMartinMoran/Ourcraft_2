package com.gabrielmartinmoran.ourcraft.ourcraft_2.player_data;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;

public class AttributesApplierService implements Runnable {

    public static int EXECUTE_EACH_TICKS = 20;
    private int BASE_HEALTH = 5 * 2;
    private double BASE_ATTACK_SPEED = 4.0d;
    private double BASE_ATTACK_DAMAGE = 2.0d;
    private double BASE_KNOCKBACK_RESISTANCE = 0d;

    @Override
    public void run() {
        for (Player player: Bukkit.getServer().getOnlinePlayers()) {
            PlayerData playerData = PlayerDataProvider.get(player);
            // Health
            int maxHealth = BASE_HEALTH + (int)Math.floor(playerData.getAttributeLevel(PlayerAttributes.RESISTANCE) / 2);
            player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(maxHealth);
            if(player.getHealth() > maxHealth) player.setHealth(maxHealth);
            // Knockback resistance
            double knockbackResistance = BASE_KNOCKBACK_RESISTANCE + (1 * (((playerData.getAttributeLevel(PlayerAttributes.RESISTANCE)- 1)/99d) / 2d));
            player.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).setBaseValue(knockbackResistance);
            // Attack speed
            double atackSpeed = BASE_ATTACK_SPEED * (1 + ((playerData.getAttributeLevel(PlayerAttributes.MELEE)- 1)/99d));
            player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(atackSpeed);
            // Attack damage
            double atackDamage = BASE_ATTACK_DAMAGE * (1 + ((playerData.getAttributeLevel(PlayerAttributes.MELEE)- 1)/99d));
            player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(atackDamage);
        }
    }
}
