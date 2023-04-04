package com.gabrielmartinmoran.ourcraft.ourcraft_2.items.ui;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.CustomItemsModelData;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.utils.ManaTextFormatter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.HashMap;

public class ManaContainerItem {


    public static ItemStack generateBottom(int currentAmount, int maxAmount) {
        ItemStack item = new ItemStack(Material.BARRIER, 1);
        ItemMeta meta = item.getItemMeta();
        int percentage = getPercentage(currentAmount, maxAmount);
        setDisplayText(meta, percentage, currentAmount, maxAmount);
        meta.setCustomModelData(getModelData(percentage).getModelData());
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack generateTop(int currentAmount, int maxAmount) {
        ItemStack item = LockedSlot.generate();
        ItemMeta meta = item.getItemMeta();
        int percentage = getPercentage(currentAmount, maxAmount);
        setDisplayText(meta, percentage, currentAmount, maxAmount);
        item.setItemMeta(meta);
        return item;
    }

    private static int getPercentage(int currentAmount, int maxAmount) {
        return (int) (currentAmount * 100f) / maxAmount;
    }

    private static void setDisplayText(ItemMeta itemMeta, int percentage, int currentAmount, int maxAmount) {
        itemMeta.setDisplayName(String.format("%s%%", percentage));
        itemMeta.setLore(Arrays.asList(String.format("%s / %s", ManaTextFormatter.formatManaAmount(currentAmount),
                ManaTextFormatter.formatManaAmount(maxAmount))));
    }

    private static CustomItemsModelData getModelData(int percentage) {
        int adjustedPercentage = percentage / 10 * 10;
        HashMap<Integer, CustomItemsModelData> map = new HashMap<Integer, CustomItemsModelData>();
        map.put(0, CustomItemsModelData.MANA_CONTAINER_0_PERCENTAGE);
        map.put(10, CustomItemsModelData.MANA_CONTAINER_10_PERCENTAGE);
        map.put(20, CustomItemsModelData.MANA_CONTAINER_20_PERCENTAGE);
        map.put(30, CustomItemsModelData.MANA_CONTAINER_30_PERCENTAGE);
        map.put(40, CustomItemsModelData.MANA_CONTAINER_40_PERCENTAGE);
        map.put(50, CustomItemsModelData.MANA_CONTAINER_50_PERCENTAGE);
        map.put(60, CustomItemsModelData.MANA_CONTAINER_60_PERCENTAGE);
        map.put(70, CustomItemsModelData.MANA_CONTAINER_70_PERCENTAGE);
        map.put(80, CustomItemsModelData.MANA_CONTAINER_80_PERCENTAGE);
        map.put(90, CustomItemsModelData.MANA_CONTAINER_90_PERCENTAGE);
        map.put(100, CustomItemsModelData.MANA_CONTAINER_100_PERCENTAGE);
        return map.get(adjustedPercentage);
    }
}
