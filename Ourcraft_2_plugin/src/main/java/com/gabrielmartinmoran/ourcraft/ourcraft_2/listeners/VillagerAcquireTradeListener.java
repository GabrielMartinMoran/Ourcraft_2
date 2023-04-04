package com.gabrielmartinmoran.ourcraft.ourcraft_2.listeners;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.mobs.villagers.CustomVillagers;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.mobs.wanderingtraders.CustomWanderingTraders;
import org.bukkit.entity.Villager;
import org.bukkit.entity.WanderingTrader;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.VillagerAcquireTradeEvent;

public class VillagerAcquireTradeListener implements Listener {

    private CustomVillagers customVillagers;
    private CustomWanderingTraders customWanderingTraders;

    public VillagerAcquireTradeListener() {
        this.customVillagers = new CustomVillagers();
        this.customWanderingTraders = new CustomWanderingTraders();
    }

    @EventHandler
    public void onVillagerAcquireTrade(VillagerAcquireTradeEvent event) {
        boolean recipeAdded = false;
        if(event.getEntity() instanceof Villager) {
            Villager villager = (Villager) event.getEntity();
            recipeAdded = this.customVillagers.addRecipe(villager, event.getRecipe());
        }
        if(event.getEntity() instanceof WanderingTrader) {
            WanderingTrader trader = (WanderingTrader) event.getEntity();
            recipeAdded = this.customWanderingTraders.addRecipe(trader, event.getRecipe());
        }
        event.setCancelled(recipeAdded);
    }
}
