package com.gabrielmartinmoran.ourcraft.ourcraft_2.listeners;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.mobs.CustomMobsManager;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class EntityDeathListener implements Listener {

    private CustomMobsManager customMobsManager;

    public EntityDeathListener() {
        this.customMobsManager = new CustomMobsManager();
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        LivingEntity entity = event.getEntity();
        Player killer = entity.getKiller();
        if (killer == null) return;
        if (this.customMobsManager.isCustomMob(entity) && this.customMobsManager.hasCustomDrop(entity)) {
            // Quitamos los drops que no son necesarios
            List<Material> naturalDropsToRemove = this.customMobsManager.getNaturalDropsToRemove(entity);
            boolean preventNaturalDrops = this.customMobsManager.preventNaturalDrops(entity);
            ArrayList<ItemStack> toRemove = new ArrayList<ItemStack>();
            for (ItemStack item : event.getDrops()) {
                if (naturalDropsToRemove.contains(item.getType()) || preventNaturalDrops) toRemove.add(item);
            }
            event.getDrops().removeAll(toRemove);
            // Agregamos los drops nuevos
            for (ItemStack drop : this.customMobsManager.getDrops(entity, killer)) {
                event.getDrops().add(drop);
            }
        }
    }
}
