package com.gabrielmartinmoran.ourcraft.ourcraft_2.serialization.serializers;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.ui.LockedSlot;
import com.google.gson.Gson;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryItemsSerializer {

    public static String serialize(Inventory inventory) {
        Gson gson = new Gson();
        String[] serializedItems = new String[inventory.getContents().length];
        for (int i = 0; i < inventory.getContents().length; i++) {
            ItemStack item = inventory.getItem(i);
            if (item == null) {
                serializedItems[i] = null;
            } else {
                serializedItems[i] = SerializedItem.serialize(item);
            }
        }
        return gson.toJson(serializedItems);
    }
}
