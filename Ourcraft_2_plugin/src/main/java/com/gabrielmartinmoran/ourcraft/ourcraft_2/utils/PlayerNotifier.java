package com.gabrielmartinmoran.ourcraft.ourcraft_2.utils;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class PlayerNotifier {

    private static void sendMessage(String playerName, String message) {
        Bukkit.getServer().getPlayer(playerName).sendMessage(message);
    }

    public static void notifySkillLevelUp(String playerName, String skill, int level) {
        String message = "" + ChatColor.GREEN + ChatColor.ITALIC + "Has subido la habilidad " +
                ChatColor.LIGHT_PURPLE + ChatColor.ITALIC + skill +
                ChatColor.GREEN + ChatColor.ITALIC + " a nivel " +
                ChatColor.GOLD + ChatColor.ITALIC + level;
        sendMessage(playerName, message);
    }
}
