package com.gabrielmartinmoran.ourcraft.ourcraft_2.serialization.serializers;

import com.google.gson.Gson;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.TagParser;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;

public class SerializedItem {
    public Material material;
    public int amount;
    @Nullable
    public String nbt;

    public static String serialize(ItemStack item) {
        SerializedItem serialized = new SerializedItem();
        serialized.material = item.getType();
        serialized.amount = item.getAmount();

        net.minecraft.world.item.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
        CompoundTag compoundTag = nmsItem.getTag();

        if (compoundTag != null) serialized.nbt = compoundTag.toString();

        return new Gson().toJson(serialized);
    }

    public static ItemStack fromJson(String json) {
        SerializedItem serialized = new Gson().fromJson(json, SerializedItem.class);
        ItemStack item = new ItemStack(serialized.material, serialized.amount);
        if (serialized.nbt != null) {
            try {
                CompoundTag compoundTag = TagParser.parseTag(serialized.nbt);
                net.minecraft.world.item.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
                nmsItem.setTag(compoundTag);
                return CraftItemStack.asBukkitCopy(nmsItem);
            } catch (CommandSyntaxException e) {
                System.out.println(e);
                return item;
            }
        }
        return item;
    }

}
