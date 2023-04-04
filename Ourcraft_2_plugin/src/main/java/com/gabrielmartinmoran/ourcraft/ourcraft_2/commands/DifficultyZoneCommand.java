package com.gabrielmartinmoran.ourcraft.ourcraft_2.commands;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.Config;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.player_data.PlayerDataProvider;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DifficultyZoneCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Es necesario que un jugador ejecute el comando");
            return true;
        }
        Player player = (Player) sender;
        if (player.getWorld().getEnvironment() != World.Environment.NORMAL) {
            sender.sendMessage("" + ChatColor.GREEN + "En esta dimensión solo hay una zona de dificultad y es " + ChatColor.GOLD + Config.MAX_DIFFICULTY_ZONES);
        } else {
            sender.sendMessage("" + ChatColor.GREEN + "Te encuentras en la zona de dificultad " + ChatColor.GOLD + PlayerDataProvider.get(sender.getName()).getDifficultyZone());
        }
        return true;
    }
}
