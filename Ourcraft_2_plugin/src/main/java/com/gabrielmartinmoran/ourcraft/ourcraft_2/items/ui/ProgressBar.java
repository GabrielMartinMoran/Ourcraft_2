package com.gabrielmartinmoran.ourcraft.ourcraft_2.items.ui;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.CustomItemsModelData;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;

public class ProgressBar {


    public static ItemStack generateAtPercentage(int percentage) {
        ItemStack item = new ItemStack(Material.BARRIER, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(String.format("%s%%", percentage));
        meta.setCustomModelData(getModelData(percentage).getModelData());
        item.setItemMeta(meta);
        return item;
    }

    protected static CustomItemsModelData getModelData(int percentage) {
        int adjustedPercentage = percentage / 10 * 10;
        HashMap<Integer, CustomItemsModelData> map = new HashMap<Integer, CustomItemsModelData>();
        map.put(0, CustomItemsModelData.PROGRESS_BAR_0_PERCENTAGE);
        map.put(10, CustomItemsModelData.PROGRESS_BAR_10_PERCENTAGE);
        map.put(20, CustomItemsModelData.PROGRESS_BAR_20_PERCENTAGE);
        map.put(30, CustomItemsModelData.PROGRESS_BAR_30_PERCENTAGE);
        map.put(40, CustomItemsModelData.PROGRESS_BAR_40_PERCENTAGE);
        map.put(50, CustomItemsModelData.PROGRESS_BAR_50_PERCENTAGE);
        map.put(60, CustomItemsModelData.PROGRESS_BAR_60_PERCENTAGE);
        map.put(70, CustomItemsModelData.PROGRESS_BAR_70_PERCENTAGE);
        map.put(80, CustomItemsModelData.PROGRESS_BAR_80_PERCENTAGE);
        map.put(90, CustomItemsModelData.PROGRESS_BAR_90_PERCENTAGE);
        map.put(100, CustomItemsModelData.PROGRESS_BAR_100_PERCENTAGE);
        return map.get(adjustedPercentage);
    }
}
