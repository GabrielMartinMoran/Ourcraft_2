package com.gabrielmartinmoran.ourcraft.ourcraft_2.commands;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.spells.SpellBook;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.spells.SpellTypes;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpellbookCommand implements CommandExecutor {

    private final String INSUFFICIENT_PERMISSIONS = "Permisos insuficientes";

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return this.resolveCommand(sender, args);
    }

    private boolean resolveCommand(CommandSender sender, String[] args) {
        if (args.length == 1 && args[0].equalsIgnoreCase("help")) {
            sender.sendMessage("" + ChatColor.DARK_PURPLE + ChatColor.BOLD + ChatColor.ITALIC + "Los tipos de conjuros permitidos son:");
            for (SpellTypes type: SpellTypes.values()) {
                if (type == SpellTypes.NONE) continue;
                sender.sendMessage("  -" + ChatColor.WHITE + " " + type.getNameColor() + type.getName() + ChatColor.WHITE + ": " + ChatColor.AQUA + type.getId());
            }
            return true;
        }
        if (args.length < 2) {
            sender.sendMessage("El comando no posee los parámetros adecuados.");
            return false;
        }
        Player player = (Player) sender;
        if (!sender.isOp()) {
            sender.sendMessage(INSUFFICIENT_PERMISSIONS);
            return true;
        }
        try {
            SpellBook sbook = new SpellBook(SpellTypes.fromInt(Integer.parseInt(args[0])), Integer.parseInt(args[1]));
            ((Player) sender).getInventory().addItem(sbook.getItem());
            return true;
        } catch (Exception e) {
            sender.sendMessage("Ha ocurrido un error al procesar el comando!");
        }
        return false;
    }
}