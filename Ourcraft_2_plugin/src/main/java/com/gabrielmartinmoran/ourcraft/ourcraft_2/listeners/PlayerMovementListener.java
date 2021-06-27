package com.gabrielmartinmoran.ourcraft.ourcraft_2.listeners;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.hydration.HydrationDecreaseEvents;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.playerdata.AttributeLevelingHandler;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.playerdata.PlayerData;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.playerdata.PlayerDataProvider;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMovementListener implements Listener {

    private AttributeLevelingHandler attributeLevelingHandler;

    public PlayerMovementListener() {
        this.attributeLevelingHandler = new AttributeLevelingHandler();
    }

    @EventHandler
    public void onPlayerMovement(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (event.getTo().getBlockX() != event.getFrom().getBlockX() || event.getTo().getBlockZ() != event.getFrom().getBlockZ()) {
            PlayerData pData = PlayerDataProvider.get(player);
            HydrationDecreaseEvents decreaseEvent = HydrationDecreaseEvents.WALK;
            if (player.isSwimming()) decreaseEvent = HydrationDecreaseEvents.SWIM;
            if (player.isSprinting()) decreaseEvent = HydrationDecreaseEvents.SPRINT;
            if (player.getGameMode() != GameMode.CREATIVE) {
                pData.getHydrationManager().consumeHydration(decreaseEvent);
                this.attributeLevelingHandler.onPlayerMove(player, player.isSwimming());
            }
        }
    }
}
