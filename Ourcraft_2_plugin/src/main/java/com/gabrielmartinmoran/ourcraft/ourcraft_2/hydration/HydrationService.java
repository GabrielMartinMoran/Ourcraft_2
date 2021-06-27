package com.gabrielmartinmoran.ourcraft.ourcraft_2.hydration;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.Config;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.playerdata.PlayerData;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.playerdata.PlayerDataProvider;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class HydrationService implements Runnable {

    public static final int EXECUTE_EACH_TICKS = 2 * 20;

    @Override
    public void run() {
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            if (player.getGameMode() != GameMode.CREATIVE) this.applyThirstyEffects(player);
        }
    }

    private void applyThirstyEffects(Player player) {
        PlayerData playerData = PlayerDataProvider.get(player);
        if (playerData.getHydrationManager().getHydrationLevel() == 0) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, EXECUTE_EACH_TICKS * 2, 1));
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, EXECUTE_EACH_TICKS * 2, 2));
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, EXECUTE_EACH_TICKS * 2, 1));
            player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, Config.TICKS_PER_SECOND * 10, 3));
            player.setHealth(player.getHealth() - 1);
            player.damage(0.01);
        } else if (playerData.getHydrationManager().getHydrationLevel() <= 2) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, EXECUTE_EACH_TICKS * 2, 0));
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, EXECUTE_EACH_TICKS * 2, 0));
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, EXECUTE_EACH_TICKS * 2, 0));
        }
    }
}
