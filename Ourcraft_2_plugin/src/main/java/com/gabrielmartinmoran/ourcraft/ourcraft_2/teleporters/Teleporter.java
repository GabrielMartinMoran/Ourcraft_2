package com.gabrielmartinmoran.ourcraft.ourcraft_2.teleporters;

import com.google.gson.Gson;
import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemFrame;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Map;

public class Teleporter {

    private Map<String, Object> serializedTargetItemFrameLocation;

    public Teleporter(ItemStack compass) {
        this.serializedTargetItemFrameLocation = getTargetItemFrameLocation(compass).serialize();
    }

    public Location getTargetItemFrameLocation() {
        return Location.deserialize(this.serializedTargetItemFrameLocation);
    }

    public static Teleporter fromStructure(ItemStack itemStack, Location itemFrameLocation) {
        if (!isValidStructure(itemFrameLocation, itemStack)) return null;
        return new Teleporter(itemStack);
    }

    public static boolean isValidStructure(Location itemFrameLocation) {
        return isValidStructure(itemFrameLocation, null);
    }

    public static boolean isValidStructure(Location itemFrameLocation, ItemStack lodedCompass) {
        int ifX = itemFrameLocation.getBlockX();
        int ifY = itemFrameLocation.getBlockY();
        int ifZ = itemFrameLocation.getBlockZ();
        World world = itemFrameLocation.getWorld();
        if(!itemFrameLocation.getChunk().isLoaded()) itemFrameLocation.getChunk().load();
        ArrayList<Entity> itemFrames = new ArrayList<Entity>(itemFrameLocation.getWorld().getNearbyEntities(itemFrameLocation, 1, 1, 1, (e) -> e.getType().equals(EntityType.ITEM_FRAME)));
        if (itemFrames.size() == 0) return false;
        ItemFrame itemFrame = (ItemFrame) itemFrames.get(0);
        ItemStack compass = lodedCompass;
        if (lodedCompass == null) compass = itemFrame.getItem();
        if (compass == null || !compass.getType().equals(Material.COMPASS)) return false;
        NBTItem nbt = new NBTItem(compass);
        if (!nbt.hasKey("LodestoneTracked")) return false;
        String targetWorld = normalizeWorldName(nbt.getString("LodestoneDimension"));
        String currentWorld = itemFrameLocation.getWorld().getName();
        return targetWorld.equals(currentWorld) &&
                world.getBlockAt(ifX, ifY - 1, ifZ).getType().equals(Material.LODESTONE) &&
                world.getBlockAt(ifX, ifY - 2, ifZ).getType().equals(Material.ANDESITE) &&
                world.getBlockAt(ifX, ifY - 2, ifZ + 1).getType().equals(Material.ANDESITE_WALL) &&
                world.getBlockAt(ifX, ifY - 2, ifZ - 1).getType().equals(Material.ANDESITE_WALL) &&
                world.getBlockAt(ifX + 1, ifY - 2, ifZ).getType().equals(Material.ANDESITE_WALL) &&
                world.getBlockAt(ifX - 1, ifY - 2, ifZ).getType().equals(Material.ANDESITE_WALL);
    }

    public static Location getTargetItemFrameLocation(ItemStack compass) {
        NBTItem nbt = new NBTItem(compass);
        String targetWorld = nbt.getString("LodestoneDimension");
        targetWorld = normalizeWorldName(targetWorld);
        NBTCompound targetCoords = nbt.getCompound("LodestonePos");
        int targetX = targetCoords.getInteger("X");
        int targetY = targetCoords.getInteger("Y");
        int targetZ = targetCoords.getInteger("Z");
        return new Location(Bukkit.getWorld(targetWorld), targetX, targetY + 1, targetZ);
    }

    private static String normalizeWorldName(String name) {
        if (name.equals("minecraft:overworld")) return "world";
        if (name.equals("minecraft:the_nether")) return "world_nether";
        if (name.equals("minecraft:the_end")) return "world_the_end";
        return name;
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public static Teleporter fromJson(String json) {
        Gson gson = new Gson();
        Teleporter data = gson.fromJson(json, Teleporter.class);
        return data;
    }

}
