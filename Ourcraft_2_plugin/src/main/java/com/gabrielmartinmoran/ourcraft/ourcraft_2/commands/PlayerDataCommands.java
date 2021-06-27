package com.gabrielmartinmoran.ourcraft.ourcraft_2.commands;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.spells.SpellBook;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.playerdata.PlayerAttributes;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.playerdata.PlayerData;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.playerdata.PlayerDataProvider;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.spells.SpellTypes;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlayerDataCommands implements CommandExecutor {

    private final String INSUFFICIENT_PERMISSIONS = "Permisos insuficientes";

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return this.resolveCommand(sender, args);
    }

    private boolean resolveCommand(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        if (!sender.isOp()) {
            sender.sendMessage(INSUFFICIENT_PERMISSIONS);
            return true;
        }
        if (args.length == 1 && args[0].equalsIgnoreCase("help")) {
            player.sendMessage("Los argumentos admitidos por el comando playerdata son:");
            player.sendMessage(" - " + ChatColor.GOLD + "playerdata get <player_name>");
            player.sendMessage(" - " + ChatColor.GOLD + "playerdata set_attr_level <player_name> <attribute> <level>");
            player.sendMessage(" - " + ChatColor.GOLD + "playerdata attributes");
            return true;
        }
        if (args.length == 2 && args[0].equalsIgnoreCase("get")) {
            PlayerData playerData = PlayerDataProvider.get(args[1]);
            if (playerData == null) {
                player.sendMessage("" + ChatColor.RED + "No se encontraron datos del jugador " + ChatColor.LIGHT_PURPLE + args[1]);
            } else {
                player.sendMessage("" + ChatColor.GREEN + "Datos del jugador " + ChatColor.LIGHT_PURPLE + args[1] + ChatColor.GREEN + ": " + ChatColor.YELLOW + playerData.toJson());
            }
            return true;
        }
        if (args.length == 1 && args[0].equalsIgnoreCase("attributes")) {
            player.sendMessage("" + ChatColor.GREEN + "Los atributos valudos son:");
            for (PlayerAttributes attribute: PlayerAttributes.values()) {
                player.sendMessage(" - " + ChatColor.YELLOW + attribute.toString());
            }
            return true;
        }
        if (args.length == 4 && args[0].equalsIgnoreCase("set_attr_level")) {
            PlayerData playerData = PlayerDataProvider.get(args[1]);
            if (playerData == null) {
                player.sendMessage("" + ChatColor.RED + "No se encontraron datos del jugador " + ChatColor.LIGHT_PURPLE + args[1]);
                return true;
            }
            try {
                PlayerAttributes attribute = PlayerAttributes.valueOf(args[2]);
                int level = Integer.parseInt(args[3]);
                playerData.setAttributeLevel(attribute, level);
            } catch (Exception e) {
                player.sendMessage("" + ChatColor.RED + e.getMessage());
            }
            player.sendMessage("" + ChatColor.GREEN + "Se modificó el nivel del atributo " + ChatColor.GOLD + args[2] + ChatColor.GREEN + " del jugador " + ChatColor.LIGHT_PURPLE + args[1]);
            return true;
        }
        sender.sendMessage("" + ChatColor.RED + "El comando no posee los parámetros adecuados!");
        return false;
    }
}