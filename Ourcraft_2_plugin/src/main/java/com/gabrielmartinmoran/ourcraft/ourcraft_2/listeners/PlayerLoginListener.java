package com.gabrielmartinmoran.ourcraft.ourcraft_2.listeners;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.player_data.PlayerDataProvider;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class PlayerLoginListener implements Listener {

    @EventHandler
    public void onPlayerRightClick(PlayerLoginEvent event) {
        Player player = event.getPlayer();

        if(!PlayerDataProvider.hasData(player.getName())) {
            // Damos la bienvenida al jugador
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(),
                    "/tellraw @a [\"\",{\"text\":\"Bienvenido \",\"color\":\"green\"},{\"text\":\"" + player.getName() + "\",\"color\":\"gold\"},{\"text\":\" a \",\"color\":\"green\"},{\"text\":\"Ourcraft 2\",\"color\":\"light_purple\"},{\"text\":\"!\",\"color\":\"green\"}]");
            PlayerDataProvider.createData(player.getName());
        }
    }
}
