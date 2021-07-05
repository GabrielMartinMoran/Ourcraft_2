package com.gabrielmartinmoran.ourcraft.ourcraft_2.serializer;

import com.google.gson.Gson;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.server.v1_16_R3.MojangsonParser;
import net.minecraft.server.v1_16_R3.NBTTagCompound;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SerializedItem {
    public Material material;
    public int amount;
    public String nbtCompound;

    public static String serialize(ItemStack item) {
        SerializedItem serialized = new SerializedItem();
        serialized.material = item.getType();
        serialized.amount = item.getAmount();
        net.minecraft.server.v1_16_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
        NBTTagCompound tagCompound = nmsItem.getTag();
        if(tagCompound != null) serialized.nbtCompound = tagCompound.toString();
        return new Gson().toJson(serialized);
    }

    public static ItemStack fromJson(String json) {
        SerializedItem serialized = new Gson().fromJson(json, SerializedItem.class);
        ItemStack item = new ItemStack(serialized.material, serialized.amount);
        ItemMeta itemMeta = item.getItemMeta();
        item.setItemMeta(itemMeta);
        if (serialized.nbtCompound != null) {
            try {
                NBTTagCompound nbtTagCompound = MojangsonParser.parse(serialized.nbtCompound);
                net.minecraft.server.v1_16_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
                nmsItem.setTag(nbtTagCompound);
                return CraftItemStack.asBukkitCopy(nmsItem);
            } catch (CommandSyntaxException e) {
                e.printStackTrace();
            }
        }
        return item;
    }

}
