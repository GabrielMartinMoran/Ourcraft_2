package com.gabrielmartinmoran.ourcraft.ourcraft_2.listeners;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.custommobs.CustomVillagers;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.VillagerAcquireTradeEvent;
import org.bukkit.event.entity.VillagerCareerChangeEvent;

public class VillagerProfessionChangeListener implements Listener {

    private CustomVillagers customVillagersSpawner;

    public VillagerProfessionChangeListener() {
        this.customVillagersSpawner = new CustomVillagers();
    }

    @EventHandler
    public void onVillagerAcquireTrade(VillagerAcquireTradeEvent event) {
        /*System.out.println(((Villager)event.getEntity()).getRecipes());
        System.out.println(event.getRecipe());*/
        Villager villager = (Villager) event.getEntity();
        this.customVillagersSpawner.addRecipe(villager);
        event.setCancelled(true);
    }

    @EventHandler
    public void onVillagerProfessionChange(VillagerCareerChangeEvent event) {
        /*
        Entity entity = event.getEntity();
        this.customVillagersSpawner.replaceVillagerTrades((Villager) entity, event.getProfession());
        event.setCancelled(true); // Cancelamos para que se guarden nuestros tradeos
        */
    }
}
