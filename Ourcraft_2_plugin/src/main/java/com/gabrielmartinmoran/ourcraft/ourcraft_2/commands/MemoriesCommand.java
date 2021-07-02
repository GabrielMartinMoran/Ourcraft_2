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

import java.util.Arrays;
import java.util.HashMap;

public class MemoriesCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Es necesario que un jugador ejecute el comando");
            return true;
        }
        if (args.length == 0) return false;
        Player player = (Player) sender;
        if (args[0].equalsIgnoreCase("help")) {
            sender.sendMessage("" + ChatColor.GREEN + "El comando memories puede utilizarse de la siguiente manera:");
            sender.sendMessage("  - " + ChatColor.GOLD + "/memories list" + ChatColor.WHITE + ": Lista tus memorias");
            sender.sendMessage("  - " + ChatColor.GOLD + "/memories add <nombre> <valor>" + ChatColor.WHITE + ": Agrega una nuevo dato a tus memorias");
            sender.sendMessage("  - " + ChatColor.GOLD + "/memories modify <nombre> <valor>" + ChatColor.WHITE + ": Modifica el valor de una de tus memorias");
            sender.sendMessage("  - " + ChatColor.GOLD + "/memories get <nombre>" + ChatColor.WHITE + ": Devuelve el valor de una de tus memorias");
            sender.sendMessage("  - " + ChatColor.GOLD + "/memories remove <nombre>" + ChatColor.WHITE + ": Elimina una de tus memorias");
            return true;
        }
        if(args[0].equalsIgnoreCase("add") && args.length > 2) {
            this.addMemory(player, args[1], this.getMemoryValue(args));
            return true;
        }
        if(args[0].equalsIgnoreCase("modify") && args.length > 2) {
            this.modifyMemory(player, args[1], this.getMemoryValue(args));
            return true;
        }
        if(args[0].equalsIgnoreCase("get") && args.length == 2) {
            this.getMemory(player, args[1]);
            return true;
        }
        if(args[0].equalsIgnoreCase("remove") && args.length == 2) {
            this.removeMemory(player, args[1]);
            return true;
        }
        if(args[0].equalsIgnoreCase("list") && args.length == 1) {
            this.listMemories(player);
            return true;
        }
        return false;
    }

    private String getMemoryValue(String[] args) {
        String value = "";
        for (int i = 2; i < args.length; i++) {
            value += args[i];
            if (i < args.length - 1) value += " ";
        }
        return value;
    }

    private HashMap<String, String> getPlayerMemories(Player player) {
        return PlayerDataProvider.get(player).getMemories();
    }

    private void addMemory(Player player, String key, String value) {
        HashMap<String, String> memories = getPlayerMemories(player);
        if (memories.containsKey(key)) {
            player.sendMessage("" + ChatColor.RED + "Ya posees una memoria con ese nombre, prueba cambiando su valor!");
            return;
        }
        memories.put(key, value);
        player.sendMessage("" + ChatColor.GREEN + "Has registrado la memoria " + ChatColor.YELLOW + key + ChatColor.GREEN + " correctamente!");
    }

    private void modifyMemory(Player player, String key, String value) {
        HashMap<String, String> memories = getPlayerMemories(player);
        if (!memories.containsKey(key)) {
            player.sendMessage("" + ChatColor.RED + "No posees una memoria con ese nombre, prueba creandola!");
            return;
        }
        memories.put(key, value);
        player.sendMessage("" + ChatColor.GREEN + "Has modificado la memoria " + ChatColor.YELLOW + key + ChatColor.GREEN + " correctamente!");
    }

    private void getMemory(Player player, String key) {
        HashMap<String, String> memories = getPlayerMemories(player);
        if (!memories.containsKey(key)) {
            player.sendMessage("" + ChatColor.RED + "No posees una memoria con ese nombre!");
            return;
        }
        player.sendMessage("Recuerdas tu memoria " + ChatColor.GREEN + key + ChatColor.WHITE + ": " + ChatColor.GOLD + memories.get(key));
    }

    private void removeMemory(Player player, String key) {
        HashMap<String, String> memories = getPlayerMemories(player);
        if (!memories.containsKey(key)) {
            player.sendMessage("" + ChatColor.RED + "No posees una memoria con ese nombre!");
            return;
        }
        memories.remove(key);
        player.sendMessage("" + ChatColor.GREEN + "Has eliminado la memoria " + ChatColor.YELLOW + key + ChatColor.GREEN + " correctamente!");
    }

    private void listMemories(Player player) {
        HashMap<String, String> memories = getPlayerMemories(player);
        if (memories.keySet().size() == 0) {
            player.sendMessage("" + ChatColor.RED + "No posees ninguna memoria registrada!");
            return;
        }
        player.sendMessage("" + ChatColor.GREEN + "Tus memorias:");
        for (String key: memories.keySet()) {
            player.sendMessage("  - " + ChatColor.GOLD + key);
        }
    }
}
