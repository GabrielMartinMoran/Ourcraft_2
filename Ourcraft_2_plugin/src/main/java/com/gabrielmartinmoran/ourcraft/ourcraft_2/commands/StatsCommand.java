package com.gabrielmartinmoran.ourcraft.ourcraft_2.commands;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.playerdata.PlayerAttributes;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.playerdata.PlayerData;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.playerdata.PlayerDataProvider;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.utils.SkillsLevelingUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StatsCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Es necesario que un jugador ejecute el comando");
            return true;
        }
        String playerName = args.length > 0 ? args[0] : sender.getName();
        this.sendPlayerStats(playerName, (Player) sender);
        return true;
    }

    private void sendPlayerStats(String playerName, Player requestor) {
        PlayerData playerData = PlayerDataProvider.get(playerName);
        if (playerData == null) {
            requestor.sendMessage("" + ChatColor.RED + "No se encontro el jugador " + ChatColor.LIGHT_PURPLE + playerName + ChatColor.RED + " en los datos del servidor!");
        }
        String levelString = "" + ChatColor.GREEN + " nivel ";
        requestor.sendMessage("Estadísticas de " + ChatColor.GOLD + playerName);
        for (PlayerAttributes attribute: PlayerAttributes.values()) {
            requestor.sendMessage(" - " + ChatColor.LIGHT_PURPLE + attribute.getDisplayName() + levelString + ChatColor.GOLD + playerData.getAttributeLevel(attribute) + ChatColor.AQUA + " (" + playerData.getAttributeXp(attribute) + "/" + SkillsLevelingUtils.getXPRequiredForHabilityLevel(playerData.getAttributeLevel(attribute)+1) + "xp)");
        }
    }
}
