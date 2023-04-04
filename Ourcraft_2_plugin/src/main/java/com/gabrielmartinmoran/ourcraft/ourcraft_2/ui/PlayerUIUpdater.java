package com.gabrielmartinmoran.ourcraft.ourcraft_2.ui;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.Config;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.player_data.PlayerData;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.player_data.PlayerDataProvider;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.spells.SpellTypes;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.spells.SpellsResolver;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.utils.DifficultyZone;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.utils.StringHelper;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PlayerUIUpdater implements Runnable {

    public static final int EXECUTE_EACH_TICKS = 5;
    private final int NIGHT_TIME_START = 12610;
    private final int HOURS_OFFSET = 6;
    private SpellsResolver spellsResolver;

    public PlayerUIUpdater() {
        this.spellsResolver = new SpellsResolver();
    }

    @Override
    public void run() {
        this.renderPlayerUI();
        this.renderDifficultyZonesChanges();
    }

    private void renderPlayerUI() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            String text = String.format("%s%s   %s %s   %s  %s  %s",
                    StringHelper.center(" ", 29),
                    StringHelper.padRight(this.getPlayerCoords(player), 27),
                    this.getPlayerWeather(player),
                    this.getTime(player),
                    this.getPlayerHidratation(player),
                    StringHelper.center(this.getPlayerMana(player), 5),
                    StringHelper.padLeft(this.getSelectedSpellCooldown(player), 30)
            );
            //text = text.replaceAll(" ", String.valueOf((char)0x93f3)); // Espacio unicode
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(text));
        }
    }

    private String getPlayerCoords(Player player) {
        Location location = player.getLocation();
        return String.format("%sX%s:%s%d %sY%s:%s%d %sZ%s:%s%d %s%s",
                ChatColor.GREEN, ChatColor.WHITE, ChatColor.AQUA, location.getBlockX(),
                ChatColor.GREEN, ChatColor.WHITE, ChatColor.AQUA, location.getBlockY(),
                ChatColor.GREEN, ChatColor.WHITE, ChatColor.AQUA, location.getBlockZ(),
                ChatColor.RESET, this.getFacingCardinalPoint(player));
    }

    private String getPlayerHidratation(Player player) {
        PlayerData pData = PlayerDataProvider.get(player);
        int max = 10;
        int current = pData.getHydrationManager().getHydrationLevel();
        String text = "";
        for (int i = 0; i < max - current; i++) {
            text += "" + ChatColor.BLACK + (char) 0x93cc;//"◎"; // Unicode de gota vacia
        }
        for (int i = 0; i < current; i++) {
            text += "" + ChatColor.DARK_AQUA + (char) 0x93cb;//"◎"; // Unicode de gota llena
        }
        return text;
    }

    private String getTime(Player player) {
        long ticksTime = player.getWorld().getTime();
        String color = "";
        if (isNight(ticksTime)) {
            color = ChatColor.DARK_BLUE.toString();
        } else {
            color = ChatColor.GOLD.toString();
        }
        int hours = (int) ticksTime / 1000;
        int hoursToDisplay = hours + HOURS_OFFSET;
        if (hoursToDisplay >= 24) hoursToDisplay -= 24;
        int minutes = (int) (((ticksTime - (hours * 1000)) * 60) / 999);
        return String.format("%s%02d:%02d", color, hoursToDisplay, minutes);
    }

    private boolean isNight(long ticks) {
        return ticks >= NIGHT_TIME_START;
    }

    private String getPlayerWeather(Player player) {
        String biome = player.getLocation().getBlock().getBiome().toString();
        if (!player.getWorld().hasStorm() || biome.contains("DESERT")) {
            return !isNight(player.getWorld().getTime()) ? "" + ChatColor.GOLD + ChatColor.BOLD + "☀" : "" + ChatColor.GOLD + ChatColor.DARK_BLUE + "☽";
        }
        if (biome.contains("SNOW")) return "" + ChatColor.WHITE + ChatColor.BOLD + "☃";
        return "" + ChatColor.DARK_AQUA + ChatColor.BOLD + "☔";
    }

    private String getFacingCardinalPoint(Player player) {
        double rot = (player.getLocation().getYaw() - 90) % 360;
        if (rot < 0) rot += 360;
        if ((0 <= rot && rot < 22.5) || (337.5 <= rot && rot < 360)) return String.valueOf((char) 0x9042);//"O ";
        if (22.5 <= rot && rot < 67.5) return String.valueOf((char) 0x9043);//"NO";
        if (67.5 <= rot && rot < 112.5) return String.valueOf((char) 0x9044);//"N ";
        if (112.5 <= rot && rot < 157.5) return String.valueOf((char) 0x9045);//"NE";
        if (157.5 <= rot && rot < 202.5) return String.valueOf((char) 0x9046);//"E ";
        if (202.5 <= rot && rot < 247.5) return String.valueOf((char) 0x9047);//"SE";
        if (247.5 <= rot && rot < 292.5) return String.valueOf((char) 0x9040);//"S ";
        if (292.5 <= rot && rot < 337.5) String.valueOf((char) 0x9041);//return "SO";
        return String.valueOf((char) 0x9041);
    }

    private String getPlayerMana(Player player) {
        PlayerData playerData = PlayerDataProvider.get(player);
        return "" + ChatColor.RESET + String.valueOf((char) 0x93f5) + ChatColor.DARK_AQUA + playerData.getManaManager().getMana();

    }

    private String getSelectedSpellCooldown(Player player) {
        PlayerData playerData = PlayerDataProvider.get(player);
        ItemStack spellCastingObject = null;
        if (this.spellsResolver.castsSpell(player.getInventory().getItemInMainHand()))
            spellCastingObject = player.getInventory().getItemInMainHand();
        if (spellCastingObject == null && this.spellsResolver.castsSpell(player.getInventory().getItemInOffHand()))
            spellCastingObject = player.getInventory().getItemInOffHand();
        if (spellCastingObject == null) return " ";
        SpellTypes spellType = this.spellsResolver.getSpellType(spellCastingObject);
        long pendingSeconds = playerData.getSpellCooldownSeconds(spellType);
        String text = "" + spellType.getNameColor() + spellType.getName() + ChatColor.WHITE;
        if (pendingSeconds > 0) {
            return text + "" + String.valueOf((char) 0x93f4) + ChatColor.RED + pendingSeconds + "s";
        }
        return text + " " + ChatColor.GREEN + "listo!";
    }

    private void renderDifficultyZonesChanges() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            // Solo checkea si esta en el overworld
            if (player.getWorld().getEnvironment() != World.Environment.NORMAL) continue;
            PlayerData playerData = PlayerDataProvider.get(player);
            int difficultyZone = DifficultyZone.calculateDifficultyZone(player.getLocation());
            if (difficultyZone != playerData.getDifficultyZone()) {
                player.sendTitle("", "" + ChatColor.GOLD + "Has entrado en la zona de dificultad " + difficultyZone, Config.TICKS_PER_SECOND * 1, Config.TICKS_PER_SECOND * 3, Config.TICKS_PER_SECOND * 1);
                playerData.setDifficultyZone(difficultyZone);
            }
        }
    }
}
