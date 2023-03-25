package com.gabrielmartinmoran.ourcraft.ourcraft_2.serialization.serializers;

import com.google.gson.Gson;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.CompoundTag;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;
import java.io.*;
import java.util.Map;

public class SerializedItem {
    public Map<String, Object> itemStack;
    public Material material;
    public int amount;

    @Nullable
    public String nbt;

    public static String serialize(ItemStack item) {
        SerializedItem serialized = new SerializedItem();
        //serialized.material = item.getType();
        //serialized.amount = item.getAmount();
        //net.minecraft.world.item.ItemStack nmsItem = ItemStack.asNMSCopy(item);

        //NBTTagCompound tagCompound = nmsItem.getTag();
        //if (tagCompound != null) serialized.nbtCompound = tagCompound.toString();

        serialized.itemStack = item.serialize();
        //Map<String, Object> serialized = item.serialize();

        net.minecraft.world.item.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
        CompoundTag compoundTag = nmsItem.getTag();

        //if (compoundTag != null) serialized.nbt = compoundTag.toString();

        if (compoundTag != null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(stream);
            try {
                NbtIo.write(compoundTag, dataOutputStream);
                serialized.nbt = new String(stream.toByteArray());
            } catch (IOException e) {
                System.out.println(e);
                serialized.nbt = null;
            }
        }

        return new Gson().toJson(serialized);
    }

    public static ItemStack fromJson(String json) {
        /*
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
        */

        /*
        Map<String, Object> serialized = new Gson().fromJson(json, new TypeToken<Map<String, Object>>() {
        }.getType());
        ItemStack item = ItemStack.deserialize(serialized);
        */
        SerializedItem serialized = new Gson().fromJson(json, SerializedItem.class);
        ItemStack item = ItemStack.deserialize(serialized.itemStack);
        if (serialized.nbt != null) {
            ByteArrayInputStream stream = new ByteArrayInputStream(serialized.nbt.getBytes());
            DataInputStream dataInputStream = new DataInputStream(stream);
            try {
                CompoundTag compoundTag = NbtIo.read(dataInputStream);

                net.minecraft.world.item.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
                nmsItem.setTag(compoundTag);
                return CraftItemStack.asBukkitCopy(nmsItem);
            } catch (IOException e) {
                System.out.println(e);
                return item;
            }
        }
        return item;
    }

}
