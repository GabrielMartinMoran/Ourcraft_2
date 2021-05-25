package com.gabrielmartinmoran.ourcraft.ourcraft_2.utils;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class PlayerUtils {
    public Entity getTargetedEntity(Player player, int range) {
        Set<Entity> entities = new HashSet<Entity>();
        for (Block b : player.getLineOfSight(null, range)) {
            Location blockLocation = b.getLocation();
            for(Entity e : b.getWorld().getNearbyEntities(b.getLocation(), 1d, 1d, 1d))
            {
                if(!entities.contains(e) && e.getEntityId() != player.getEntityId())entities.add(e);
            }
        }

        Entity targetedEntity = null;
        double distance = Double.MAX_VALUE;

        for (Entity e : entities) {
            double tmpDistance = e.getLocation().distance(player.getLocation());
            if (targetedEntity == null || tmpDistance < distance) {
                targetedEntity = e;
                distance = tmpDistance;
            }
        }

        return targetedEntity;
    }

    public Block getTargetedBlock(Player player, int range) {
        Set<Material> airMaterialSet = new HashSet<Material>();
        airMaterialSet.add(Material.AIR);
        return player.getTargetBlock(airMaterialSet, range);
    }

    public ArrayList<Player> getNearbyPlayers(Player player, double range){
        ArrayList<Player> nearby = new ArrayList<Player>();
        for (Entity e : player.getNearbyEntities(range, range, range)){
            if (e instanceof Player){
                nearby.add((Player) e);
            }
        }
        return nearby;
    }
}
