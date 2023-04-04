package com.gabrielmartinmoran.ourcraft.ourcraft_2.items.ui.blocks;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.CustomItemsModelData;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ManaTurretUI {
    public static ItemStack generate() {
        ItemStack item = new ItemStack(Material.BARRIER, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(" ");
        meta.setCustomModelData(CustomItemsModelData.MANA_TURRET_UI.getModelData());
        item.setItemMeta(meta);
        return item;
    }
}
