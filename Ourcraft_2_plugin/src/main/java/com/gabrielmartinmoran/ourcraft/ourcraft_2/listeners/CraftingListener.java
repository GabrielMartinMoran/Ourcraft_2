package com.gabrielmartinmoran.ourcraft.ourcraft_2;

import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

public class CraftingListener implements Listener {

    @EventHandler
    public void onCraft(CraftItemEvent event) {
        HumanEntity hw = event.getWhoClicked();
        ItemStack result = event.getRecipe().getResult();
        System.out.println(hw.getName() + " intenta craftear " + result.getType().toString());
        if(result.getType().equals(Material.IRON_SWORD)) {
            event.setCancelled(true);
            event.getInventory().setResult(new ItemStack(Material.AIR));
        }
    }
}
