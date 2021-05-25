package com.gabrielmartinmoran.ourcraft.ourcraft_2.ui;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class PlayerUIUpdater implements Runnable {

    private final int EXECUTE_EACH_TICKS = 5;
    private int tickCounter = 0;

    public PlayerUIUpdater() {
    }

    @Override
    public void run() {
        if (tickCounter < EXECUTE_EACH_TICKS) {
            tickCounter++;
            return;
        }
        tickCounter = 0;
        for(Player player: Bukkit.getOnlinePlayers()){
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(this.getPlayerCoords(player)));
        }
    }

    private String getPlayerCoords(Player player) {
        Location location = player.getLocation();
        return String.format("Coordenadas: X: %d  Y: %d  Z: %d",location.getBlockX(),location.getBlockY(), location.getBlockZ());
    }
}
