package com.gabrielmartinmoran.ourcraft.ourcraft_2.items.ui;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.CustomItemsModelData;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ManaLinkedIndicator {


    public static ItemStack generateConnected() {
        ItemStack item = new ItemStack(Material.BARRIER, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("Conectado");
        meta.setCustomModelData(CustomItemsModelData.MANA_LINK_CONNECTED.getModelData());
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack generateDisconnected() {
        ItemStack item = new ItemStack(Material.BARRIER, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("Desconectado");
        meta.setCustomModelData(CustomItemsModelData.MANA_LINK_DISCONNECTED.getModelData());
        item.setItemMeta(meta);
        return item;
    }
}
