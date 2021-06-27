package com.gabrielmartinmoran.ourcraft.ourcraft_2.listeners;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.crafting.RecipesLoader;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.customitems.CustomItem;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareSmithingEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.SmithingInventory;
import org.bukkit.inventory.SmithingRecipe;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

public class SmithingListener implements Listener {

    @EventHandler
    public void onPrepareSmithing(PrepareSmithingEvent event) {
        SmithingInventory inventory = event.getInventory();
        ItemStack tool = inventory.getItem(0);
        ItemStack modifier = inventory.getItem(1);
        for (CustomItem customItem : RecipesLoader.getSmithingRecipes()) {
            SmithingRecipe recipe = (SmithingRecipe) customItem.getRecipe();
            if (isSameItemType(recipe.getBase().getItemStack(), tool) && isSameItemType(recipe.getAddition().getItemStack(), modifier)) {
                ItemStack item = customItem.getItem();
                // Copiamos encantamientos
                item.addEnchantments(tool.getEnchantments());
                // Copiamos durabilidad
                if (tool.getItemMeta() instanceof Damageable) {
                    Damageable toolMeta = (Damageable) tool.getItemMeta();
                    Damageable itemMeta = (Damageable) item.getItemMeta();
                    itemMeta.setDamage(toolMeta.getDamage());
                    item.setItemMeta((ItemMeta) itemMeta);
                }
                event.setResult(item);
                return;
            }
        }
    }

    private boolean isSameItemType(ItemStack original, ItemStack checking) {
        if (original == null || original.getType().equals(Material.AIR) || checking == null || checking.getType().equals(Material.AIR)) {
            return false;
        }
        ItemMeta oIMeta = original.getItemMeta();
        ItemMeta cIMeta = checking.getItemMeta();
        return original.getType().equals(checking.getType()) &&
                ((!oIMeta.hasCustomModelData() && !cIMeta.hasCustomModelData()) ||
                        (oIMeta.hasCustomModelData() && cIMeta.hasCustomModelData() && oIMeta.getCustomModelData() == cIMeta.getCustomModelData()));
    }
}
