package com.gabrielmartinmoran.ourcraft.ourcraft_2.commands;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.SpellBook;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.spells.SpellTypes;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpellbookCommand implements CommandExecutor {

    private final String INSUFFICIENT_PERMISSIONS = "Permisos insuficientes";

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("spellbook")) {
            return this.resolveSpellbookCommand(sender, args);
        }
        return false;
    }

    private boolean resolveSpellbookCommand(CommandSender sender, String[] args) {
        if (args.length < 2) {
            sender.sendMessage("El comando no posee los parámetros adecuados, esperado: /spellbook <tipo> <nivel>");
            return false;
        }
        Player player = (Player) sender;
        if (!sender.isOp()) {
            sender.sendMessage(INSUFFICIENT_PERMISSIONS);
            return false;
        }
        try {
            SpellBook sbook = new SpellBook(SpellTypes.fromInt(Integer.parseInt(args[0])), Integer.parseInt(args[1]));
            ((Player) sender).getInventory().addItem(sbook.getItem());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            sender.sendMessage("Ha ocurrido un error al procesar el comando!");
        }
        return false;
    }
}