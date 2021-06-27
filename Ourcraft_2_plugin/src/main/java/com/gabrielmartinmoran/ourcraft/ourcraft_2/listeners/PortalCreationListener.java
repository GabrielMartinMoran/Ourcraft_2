package com.gabrielmartinmoran.ourcraft.ourcraft_2.listeners;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.Config;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.crafting.RecipesLocker;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.playerdata.PlayerAttributes;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.PortalCreateEvent;

import java.util.List;

public class PortalCreationListener implements Listener {

    private RecipesLocker recipesLocker;

    public PortalCreationListener() {
        this.recipesLocker = new RecipesLocker();
    }


    @EventHandler
    public void onPortalCreation(PortalCreateEvent event) {
        Player activator = this.getActivatorPlayer(event);
        if(this.isNetherPortal(event) && (activator == null || !this.recipesLocker.canActivateNetherPortal(activator))) {
            if(activator != null) activator.sendMessage("" + ChatColor.RED + "Tu nivel de " + ChatColor.LIGHT_PURPLE + PlayerAttributes.MAGIC.getDisplayName() + ChatColor.RED +" no es lo suficientemente alto como para activar este portal!");
            event.setCancelled(true);
            return;
        }
    }

    private Player getActivatorPlayer(PortalCreateEvent event) {
        Location loc = event.getBlocks().stream().findFirst().get().getLocation();
        List<Player> players = event.getWorld().getPlayers();
        Player nearest = null;
        double nearestDistance = 0;
        for (Player player: players) {
            double distance = player.getLocation().distance(loc);
            if (distance <= Config.PORTAl_CREATION_MAX_DETECTION_RANGE && (distance < nearestDistance || nearest == null)){
                nearest = player;
                nearestDistance = distance;
            }
        }
        return nearest;
    }

    private boolean isNetherPortal(PortalCreateEvent event) {
        for (BlockState blockState: event.getBlocks()) {
            if(blockState.getBlock().getType().equals(Material.OBSIDIAN)) return true;
        }
        return false;
    }
}
