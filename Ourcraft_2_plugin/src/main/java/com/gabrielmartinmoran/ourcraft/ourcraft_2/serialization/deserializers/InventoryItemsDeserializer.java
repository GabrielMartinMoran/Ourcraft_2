package com.gabrielmartinmoran.ourcraft.ourcraft_2.serialization.deserializers;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.ui.LockedSlot;
import com.gabrielmartinmoran.ourcraft.ourcraft_2.serialization.serializers.SerializedItem;
import com.google.gson.Gson;
import org.bukkit.inventory.ItemStack;

public class InventoryItemsDeserializer {

    public static ItemStack[] deserialize(String serialized) {
        Gson gson = new Gson();
        String[] serializedItems = gson.fromJson(serialized, String[].class);
        ItemStack[] items = new ItemStack[serializedItems.length];
        for (int i = 0; i < serializedItems.length; i++) {
            String item = serializedItems[i];
            if (item == null) {
                items[i] = null;
            } else {
                items[i] = SerializedItem.fromJson(item);
            }
        }
        return items;
    }
}
