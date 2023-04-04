package com.gabrielmartinmoran.ourcraft.ourcraft_2.listeners;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.player_data.PlayerAttributes;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.player_data.PlayerData;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.player_data.PlayerDataProvider;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;

public class EntityRegainHealthListener implements Listener {

    @EventHandler
    public void onRegainHealth(EntityRegainHealthEvent event) {
        if (event.getEntity() instanceof Player) {
            PlayerData playerData = PlayerDataProvider.get(event.getEntity().getName());
            playerData.addAttributeXp(PlayerAttributes.RESISTANCE, event.getAmount() / 2d);
        }
    }
}
