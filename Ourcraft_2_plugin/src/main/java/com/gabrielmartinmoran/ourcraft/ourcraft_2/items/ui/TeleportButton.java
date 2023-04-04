package com.gabrielmartinmoran.ourcraft.ourcraft_2.items.ui;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.CustomItemsModelData;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class TeleportButton {


    public static ItemStack generate(int manaCost) {
        ItemStack item = new ItemStack(Material.BARRIER, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("Teletransportarse");
        meta.setLore(Arrays.asList(String.format("Coste del teletransporte: %s", manaCost)));
        meta.setCustomModelData(CustomItemsModelData.TELEPORT_BUTTON.getModelData());
        item.setItemMeta(meta);
        return item;
    }
}
