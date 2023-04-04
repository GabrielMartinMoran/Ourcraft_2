package com.gabrielmartinmoran.ourcraft.ourcraft_2.items.ui.manachannels;

import com.gabrielmartinmoran.ourcraft.ourcraft_2.items.CustomItemsModelData;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class ManaTransmissionChannel {


    public static ItemStack generate() {
        ItemStack barrier = new ItemStack(Material.BARRIER);
        ItemMeta barrierMeta = barrier.getItemMeta();
        barrierMeta.setDisplayName("Canal de transmisión de maná");
        barrierMeta.setLore(Arrays.asList(new String[]{"Transmite maná en la frecuencia", "del ítem utilizado"}));
        barrierMeta.setCustomModelData(CustomItemsModelData.MANA_TRANSMISSION_CHANNEL.getModelData());
        barrier.setItemMeta(barrierMeta);
        return barrier;
    }
}
