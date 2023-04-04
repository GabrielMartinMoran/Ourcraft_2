package com.gabrielmartinmoran.ourcraft.ourcraft_2.items.ui;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.CustomItemsModelData;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SlotValidation {

    public static ItemStack generateValid() {
        ItemStack item = new ItemStack(Material.BARRIER, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("Válido");
        meta.setCustomModelData(CustomItemsModelData.VALID_UI_SLOT.getModelData());
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack generateInvalid() {
        ItemStack item = new ItemStack(Material.BARRIER, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("Inválido");
        meta.setCustomModelData(CustomItemsModelData.INVALID_UI_SLOT.getModelData());
        item.setItemMeta(meta);
        return item;
    }
}